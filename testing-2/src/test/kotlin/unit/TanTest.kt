package unit

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import trigonometric.Tan
import trigonometric.TrigonometricFunction
import kotlin.math.PI
import kotlin.math.tan

class TanTest {
    private class KotlinSin : TrigonometricFunction {
        override fun calc(x: Double, eps: Double): Double = kotlin.math.sin(x)
    }

    private class KotlinCos : TrigonometricFunction {
        override fun calc(x: Double, eps: Double): Double = kotlin.math.cos(x)
    }

    private val tanCalculator = Tan(
        sinCalculator = KotlinSin(),
        cosCalculator = KotlinCos()
    )

    @ParameterizedTest
    @ValueSource(doubles = [0.0, PI / 6, PI / 4, PI / 3, -PI / 6, -PI / 4, -PI / 3])
    fun testTan(x: Double) {
        val eps = 1e-10
        val expected = tan(x)


        val actual = tanCalculator.calc(x, eps)
        assertEquals(expected, actual, eps, "Failed for x = $x")
    }

    @ParameterizedTest
    @ValueSource(doubles = [PI / 2, -PI / 2])
    fun testTanUncountable(x: Double) {
        val eps = 1e-10

        assertThrows<IllegalArgumentException> {
            tanCalculator.calc(x, eps)
        }
    }
}