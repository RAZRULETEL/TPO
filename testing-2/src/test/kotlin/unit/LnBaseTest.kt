package unit

import logarithm.LnBase
import logarithm.LogarithmFunction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import kotlin.math.log

class LnBaseTest {

    private class KotlinLn : LogarithmFunction {
        override fun calc(x: Double, eps: Double): Double = kotlin.math.ln(x)
    }

    companion object {
        @JvmStatic
        fun logInputs(): List<Arguments> = listOf(
            Arguments.of(10.0, 2.0, 1e-6), // log_2(10)
            Arguments.of(8.0, 2.0, 1e-6),  // log_2(8) = 3
            Arguments.of(1.0, 10.0, 1e-6), // log_10(1) = 0
            Arguments.of(100.0, 10.0, 1e-6), // log_10(100) = 2
            Arguments.of(0.5, 2.0, 1e-6),  // log_2(0.5) = -1
            Arguments.of(Math.E, Math.E, 1e-6) // log_e(e) = 1
        )
    }

    @ParameterizedTest
    @MethodSource("logInputs")
    fun testLnBase(x: Double, base: Double, eps: Double) {
        val lnBaseCalculator = LnBase(base, KotlinLn())
        val expected = log(x, base)

        val actual = lnBaseCalculator.calc(x, eps)
        assertEquals(expected, actual, eps, "Failed for x = $x, base = $base")
    }

    @ParameterizedTest
    @ValueSource(doubles = [-1.0, 0.0, 1.0])
    fun testLnBaseInvalidBase(base: Double) {
        val lnBaseCalculator = LnBase(base, KotlinLn())
        val eps = 1e-6

        assertThrows<IllegalArgumentException> {
            lnBaseCalculator.calc(10.0, eps)
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = [-1.0, 0.0])
    fun testLnBaseInvalidX(x: Double) {
        val lnBaseCalculator = LnBase(2.0, KotlinLn())
        val eps = 1e-6

        assertThrows<IllegalArgumentException> {
            lnBaseCalculator.calc(x, eps)
        }
    }
}