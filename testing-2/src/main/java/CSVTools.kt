import java.io.File
import java.io.InputStream

object CSVTools {
    fun saveToCSVFile(
        filename: String,
        fromX: Double,
        toX: Double,
        step: Double,
        eps: Double,
        func: (Double, Double) -> Double
    ) {
        require(step != 0.0) { "Step must not be zero" }

        val xValues = if ((step > 0 && fromX > toX) || (step < 0 && fromX < toX)) {
            emptyList()
        } else {
            generateSequence(fromX) { previous ->
                val next = previous + step
                when {
                    step > 0 && next > toX -> null
                    step < 0 && next < toX -> null
                    else -> next
                }
            }.toList()
        }

        val lines = mutableListOf("x,result")
        xValues.forEach { x ->
            try {
                val y = func(x, eps)
                lines.add("$x,$y")
            } catch (e: Exception) {
                lines.add("$x,${e.message}")
            }

        }

        File(filename).writeText(lines.joinToString("\n"))
    }

    fun loadFromCSVFile(filename: String): Map<Double, Double?> {
        // Load the file as a classpath resource (for Gradle test resources)
        val inputStream: InputStream = CSVTools::class.java.classLoader.getResourceAsStream(filename)
            ?: throw IllegalArgumentException("File '$filename' not found in resources directory")

        val lines = inputStream.bufferedReader().use { it.readLines() }
        val resultMap = mutableMapOf<Double, Double?>()

        // Skip header and process data lines
        lines.drop(1).forEach { line ->
            val parts = line.split(",").map { it.trim() }
            if (parts.size == 2) {
                val x = parts[0].toDoubleOrNull()
                val y = parts[1].toDoubleOrNull()
                if (x != null) {
                    resultMap[x] = y // y is null if parsing fails
                }
            }
        }

        return resultMap
    }

    fun createGraphFromCsv(
        csvFileName: String,
        outputImagePath: String,
        title: String = "Function Graph",
        xAxisLabel: String = "X",
        yAxisLabel: String = "Y",
        width: Int = 800,
        height: Int = 600,
        yMin: Double = -50.0,
        yMax: Double = 50.0
    ) {
        // Load data from CSV
        val dataMap = loadFromCSVFile(csvFileName)

        // Prepare data for plotting
        val xData = mutableListOf<Double>()
        val yData = mutableListOf<Double>()

        dataMap.forEach { (x, y) ->
            if (y != null && y in yMin..yMax) {
                xData.add(x)
                yData.add(y)
            }
        }

        val chart = org.knowm.xchart.XYChartBuilder()
            .width(width)
            .height(height)
            .title(title)
            .xAxisTitle(xAxisLabel)
            .yAxisTitle(yAxisLabel)
            .build()

        // Add data series to chart
        val series = chart.addSeries("f(x)", xData, yData)

        series.lineStyle = org.knowm.xchart.style.lines.SeriesLines.NONE

        // Save chart to file
        org.knowm.xchart.BitmapEncoder.saveBitmap(
            chart,
            outputImagePath,
            org.knowm.xchart.BitmapEncoder.BitmapFormat.PNG
        )

        println("Graph saved: $outputImagePath")
    }


    fun saveToCSVFile(
        filename: String,
        xValues: Collection<Double>,
        eps: Double,
        func: (Double, Double) -> Double,
        path: String
    ) {
        val lines = mutableListOf("x,result")
        xValues.forEach { x ->
            try {
                val y = func(x, eps)
                lines.add("$x,$y")
            } catch (e: Exception) {
                lines.add("$x,${e.message}")
            }

        }

        File(path, filename).writeText(lines.joinToString("\n"))
    }

}