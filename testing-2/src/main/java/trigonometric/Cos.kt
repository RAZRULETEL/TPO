package trigonometric

import kotlin.math.PI

//class Cos : TrigonometricFunction {
//    override fun calc(x: Double, eps: Double): Double {
//        require(x.isFinite()) { "x must be a finite number" }
//        require(eps > 0) { "eps must be a positive number" }
//
//        val pi = Math.PI
//        var normalizedX = x % (2 * pi)
//        if (normalizedX > pi) normalizedX -= 2 * pi
//        if (normalizedX < -pi) normalizedX += 2 * pi
//
//        var result = 1.0
//        var term = 1.0
//        var n = 1
//
//        while (kotlin.math.abs(term) >= eps) {
//            term *= -normalizedX * normalizedX / ((2 * n - 1) * (2 * n))
//            result += term
//            n++
//        }
//
//        return result
//    }
//}

class Cos(private val sin: TrigonometricFunction = Sin()) : TrigonometricFunction {
    override fun calc(x: Double, epsilon: Double): Double {
        require(x.isFinite()) { "x must be finite" }
        require(epsilon > 0) { "epsilon must be greater than 0" }

        return sin.calc(x + PI / 2, epsilon)
    }
}