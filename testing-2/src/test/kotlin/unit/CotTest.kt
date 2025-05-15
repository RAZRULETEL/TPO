package unit

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import trigonometric.Cot
import trigonometric.Tan
import trigonometric.TrigonometricFunction
import kotlin.math.PI
import kotlin.math.tan

class CotTest {
    private class KotlinSin : TrigonometricFunction {
        override fun calc(x: Double, eps: Double): Double = kotlin.math.sin(x)
    }

    private class KotlinCos : TrigonometricFunction {
        override fun calc(x: Double, eps: Double): Double = kotlin.math.cos(x)
    }

    private val cotCalculator = Cot(
        sinCalculator = KotlinSin(),
        cosCalculator = KotlinCos()
    )

    @ParameterizedTest
    @ValueSource(doubles = [PI / 2, - PI /2, PI / 6, PI / 4, PI / 3, -PI / 6, -PI / 4, -PI / 3])
    fun testCot(x: Double) {
        val eps = 1e-10
        val expected = 1.0 / tan(x)


        val actual = cotCalculator.calc(x, eps)
        assertEquals(expected, actual, eps, "Failed for x = $x")
    }

    @ParameterizedTest
    @ValueSource(doubles = [0.0, PI, -PI])
    fun testCotUncountable(x: Double) {
        val eps = 1e-10

        assertThrows<IllegalArgumentException> {
            cotCalculator.calc(x, eps)
        }
    }
}