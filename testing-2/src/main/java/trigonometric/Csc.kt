package trigonometric

import kotlin.math.PI
import kotlin.math.abs

class Csc(
    private val sinCalculator: TrigonometricFunction = Sin()
) : TrigonometricFunction {
    override fun calc(x: Double, eps: Double): Double {
        require(x.isFinite()) { "x must be a finite number" }
        require(eps > 0) { "eps must be a positive number" }
        require(abs(x % PI) > eps) { "x must not be near a multiple of PI" }

        val sinX = sinCalculator.calc(x, eps)

        return 1.0 / sinX
    }
}