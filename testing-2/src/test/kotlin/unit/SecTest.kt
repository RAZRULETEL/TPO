package unit

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import trigonometric.Sec
import trigonometric.TrigonometricFunction
import kotlin.math.PI
import kotlin.math.cos

class SecTest {
    private class KotlinCos : TrigonometricFunction {
        override fun calc(x: Double, eps: Double): Double = cos(x)
    }

    private val secCalculator = Sec(KotlinCos())

    @ParameterizedTest
    @ValueSource(doubles = [0.0, PI / 3, PI / 4, -PI / 3, -PI / 4])
    fun testSec(x: Double) {
        val eps = 1e-10
        val expected = 1.0 / cos(x)

        val actual = secCalculator.calc(x, eps)
        assertEquals(expected, actual, eps, "Failed for x = $x")
    }

    @ParameterizedTest
    @ValueSource(doubles = [PI / 2, -PI / 2])
    fun testSecUndefined(x: Double) {
        val eps = 1e-10

        assertThrows<IllegalArgumentException> {
            secCalculator.calc(x, eps)
        }
    }
}