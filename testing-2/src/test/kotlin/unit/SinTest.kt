package unit

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import trigonometric.Sin
import kotlin.math.PI
import kotlin.math.sin

class SinTest {
    private val sinCalculator = Sin()

    @ParameterizedTest
    @ValueSource(doubles = [0.0, PI / 6, PI / 4, PI / 3, PI / 2, PI, -PI / 6, -PI / 4, -PI / 3, -PI / 2, -PI])
    fun testSin(x: Double) {
        val eps = 1e-10
        val expected = sin(x)
        val actual = sinCalculator.calc(x, eps)

        assertEquals(expected, actual, eps, "Failed for x = $x")
    }
}