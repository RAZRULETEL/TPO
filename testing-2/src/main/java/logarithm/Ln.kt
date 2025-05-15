package logarithm

import kotlin.math.abs

class Ln : LogarithmFunction {
    override fun calc(x: Double, eps: Double): Double {
        require(x.isFinite()) { "x must be a finite number" }
        require(eps > 0) { "eps must be a positive number" }
        require(x > 0) { "x must be positive" }

        if (x == 1.0) {
            return 0.0
        }

        val xVal = abs((x - 1.0) / (x + 1.0))
        var sum = 0.0
        var term = xVal
        var k = 1

        while (term > eps) {
            sum += term
            term *= xVal * xVal / (2 * k + 1.0) * (2 * k - 1.0)
            k++
        }
        return (if (x < 1) -2 else 2) * sum
    }
}