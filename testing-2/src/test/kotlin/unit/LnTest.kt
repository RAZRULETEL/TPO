package unit

import logarithm.Ln
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.math.ln

class LnTest {
    private val lnCalculator = Ln()

    @ParameterizedTest
    @ValueSource(doubles = [1.0, 2.0, 0.5, Math.E, 10.0, 0.1])
    fun testLn(x: Double) {
        val eps = 1e-10
        val expected = ln(x)

        val actual = lnCalculator.calc(x, eps)
        assertEquals(expected, actual, 1e-6, "Failed for x = $x")
    }

    @ParameterizedTest
    @ValueSource(doubles = [-1.0, 0.0, -0.5])
    fun testLnUndefined(x: Double) {
        val eps = 1e-6

        assertThrows<IllegalArgumentException> {
            lnCalculator.calc(x, eps)
        }
    }
}