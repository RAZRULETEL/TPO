package trigonometric

import kotlin.math.PI
import kotlin.math.abs

class Sin: TrigonometricFunction {
    override fun calc(x: Double, eps: Double): Double {
        require(x.isFinite()) { "x must be a finite number" }
        require(eps > 0) { "eps must be a positive number" }

        val pi = Math.PI
        var normalizedX = x % (2 * pi)
        if (normalizedX > pi) normalizedX -= 2 * pi
        if (normalizedX < -pi) normalizedX += 2 * pi

        var result = 0.0
        var term = normalizedX
        var n = 1

        while (Math.abs(term) >= eps) {
            result += term
            term *= -normalizedX * normalizedX / ((2 * n) * (2 * n + 1))
            n++
        }

        return result
    }
}
