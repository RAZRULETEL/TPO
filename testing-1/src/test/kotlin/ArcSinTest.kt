import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import part1.arcSinSeries
import kotlin.math.asin
import kotlin.test.Test
import kotlin.test.assertEquals

class ArcSinTest {
    @ParameterizedTest
    @CsvSource(
        "0.0, 0.0",
        "0.5, 0.5235987756",
        "-0.5, -0.5235987756"
    )
    fun testArcSinValues(x: Double, expected: Double) {
        assertEquals(expected, arcSinSeries(x, 25), 1e-5)
    }

    @Test
    fun testArcSinOutOfBounds() {
        assertThrows<IllegalArgumentException> {
            arcSinSeries(2.0, 10)
        }
        assertThrows<IllegalArgumentException> {
            arcSinSeries(-2.0, 10)
        }
        assertDoesNotThrow {
            arcSinSeries(1.0, -10)
        }
    }

    @Test
    fun testArcSinBounds() {
        assertEquals(asin(1.0), arcSinSeries(1.0, 500_000), 1e-3)
        assertEquals(asin(-1.0), arcSinSeries(-1.0, 500_000), 1e-3)
    }
}