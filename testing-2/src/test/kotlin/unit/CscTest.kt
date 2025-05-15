package unit

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import trigonometric.Csc
import trigonometric.TrigonometricFunction
import kotlin.math.PI
import kotlin.math.sin

class CscTest {
    private class KotlinSin : TrigonometricFunction {
        override fun calc(x: Double, eps: Double): Double = sin(x)
    }

    private val cscCalculator = Csc(KotlinSin())

    @ParameterizedTest
    @ValueSource(doubles = [PI / 6, PI / 4, PI / 3, -PI / 6, -PI / 4, -PI / 3])
    fun testCsc(x: Double) {
        val eps = 1e-10
        val expected = 1.0 / sin(x)

        val actual = cscCalculator.calc(x, eps)
        assertEquals(expected, actual, eps, "Failed for x = $x")
    }

    @ParameterizedTest
    @ValueSource(doubles = [0.0, PI, -PI])
    fun testCscUndefined(x: Double) {
        val eps = 1e-10

        assertThrows<IllegalArgumentException> {
            cscCalculator.calc(x, eps)
        }
    }
}