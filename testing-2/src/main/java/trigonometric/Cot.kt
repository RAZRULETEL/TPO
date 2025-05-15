package trigonometric

import kotlin.math.abs

class Cot(
    private val sinCalculator: TrigonometricFunction = Sin(),
    private val cosCalculator: TrigonometricFunction = Cos()
) : TrigonometricFunction {
    override fun calc(x: Double, eps: Double): Double {
        require(x.isFinite()) { "x must be a finite number" }
        require(eps > 0) { "eps must be a positive number" }
        require(abs(x % Math.PI) > eps) { "x must not be a multiple of pi" }

        val sinX = sinCalculator.calc(x, eps)
        val cosX = cosCalculator.calc(x, eps)

        return cosX / sinX
    }
}