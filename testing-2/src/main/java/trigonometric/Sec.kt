package trigonometric

import kotlin.math.PI
import kotlin.math.abs

class Sec(
    private val cosCalculator: TrigonometricFunction = Cos()
) : TrigonometricFunction {
    override fun calc(x: Double, eps: Double): Double {
        require(x.isFinite()) { "x must be a finite number" }
        require(eps > 0) { "eps must be a positive number" }
        require(abs(abs(x % PI) - PI / 2) > eps) { "x must not be near a multiple of pi/2 + n*pi" }

        val cosX = cosCalculator.calc(x, eps)

        return 1.0 / cosX
    }
}