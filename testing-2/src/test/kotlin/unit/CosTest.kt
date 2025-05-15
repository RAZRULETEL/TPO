package unit

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import trigonometric.Cos
import kotlin.math.PI
import kotlin.math.cos

class CosTest {
    private val cosCalculator = Cos()

    @ParameterizedTest
    @ValueSource(doubles = [0.0, PI / 6, PI / 4, PI / 3, PI / 2, PI, -PI / 6, -PI / 4, -PI / 3, -PI / 2, -PI])
    fun testCos(x: Double) {
        val eps = 1e-10
        val expected = cos(x)
        val actual = cosCalculator.calc(x, eps)

        assertEquals(expected, actual, eps, "Failed for x = $x")
    }
}