import logarithm.Ln
import logarithm.LnBase
import trigonometric.Cos
import trigonometric.Cot
import trigonometric.Csc
import trigonometric.Sec
import trigonometric.Sin
import trigonometric.Tan
import kotlin.math.PI

class Main {
}

fun main() {
//    println("Hello, World!")
    val dir = "res"
    val sv = CSVTools.loadFromCSVFile("gen.csv").keys
    CSVTools.saveToCSVFile("sin.csv", sv, 1e-12, Sin()::calc, dir)
    CSVTools.saveToCSVFile("cos.csv", sv, 1e-12, Cos()::calc, dir)
    CSVTools.saveToCSVFile("tan.csv", sv, 1e-12, Tan()::calc, dir)
    CSVTools.saveToCSVFile("cot.csv", sv, 1e-12, Cot()::calc, dir)
    CSVTools.saveToCSVFile("sec.csv", sv, 1e-12, Sec()::calc, dir)
    CSVTools.saveToCSVFile("csc.csv", sv, 1e-12, Csc()::calc, dir)

    val svLn = CSVTools.loadFromCSVFile("gen_ln.csv").keys
    CSVTools.saveToCSVFile("ln.csv", svLn,  1e-6, Ln()::calc, dir)

    CSVTools.saveToCSVFile("log2.csv", svLn, 1e-10, LnBase(2.0)::calc, dir)
    CSVTools.saveToCSVFile("log3.csv", svLn, 1e-10, LnBase(3.0)::calc, dir)
    CSVTools.saveToCSVFile("log5.csv", svLn, 1e-10, LnBase(5.0)::calc, dir)
    CSVTools.saveToCSVFile("log10.csv", svLn, 1e-10, LnBase(10.0)::calc, dir)

//    CSVTools.createGraphFromCsv("sin.csv", "sin.png", "Sin function")
//    CSVTools.createGraphFromCsv("cos.csv", "cos.png", "Cos function")
//    CSVTools.createGraphFromCsv("tan.csv", "tan.png", "Tan function")
//    CSVTools.createGraphFromCsv("cot.csv", "cot.png", "Cot function")
//    CSVTools.createGraphFromCsv("sec.csv", "sec.png", "Sec function")
//    CSVTools.createGraphFromCsv("csc.csv", "csc.png", "Csc function")
//    CSVTools.createGraphFromCsv("ln.csv", "ln.png", "Ln function")
//    CSVTools.createGraphFromCsv("log2.csv", "ln2.png", "Log with base 2 function")
//    CSVTools.createGraphFromCsv("log3.csv", "ln3.png", "Log with base 3 function")
//    CSVTools.createGraphFromCsv("log5.csv", "ln5.png", "Log with base 5 function")
//    CSVTools.createGraphFromCsv("log10.csv", "ln10.png", "Log with base 10 function")

//    CSVTools.saveToCSVFile("trigFunc.csv", -10.0, 10.0, 0.0001, 1e-10, TrigFunc()::calc)
//    CSVTools.saveToCSVFile("logFunction.csv", -10.0, 10.0, 0.0001, 1e-10, LnFunc()::calc)
//    CSVTools.saveToCSVFile("fullFunc.csv", -10.0, 10.0, 0.0001, 1e-10, Func()::calc)

//    CSVTools.createGraphFromCsv("trigFunc.csv", "trigFunc.png", "Trigonometric function")
//    CSVTools.createGraphFromCsv("logFunction.csv", "logFunction.png", "Logarithmic function")
//    CSVTools.createGraphFromCsv("fullFunc.csv", "fullFunc.png", "Full function")
}