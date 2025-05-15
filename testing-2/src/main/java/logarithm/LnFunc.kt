package logarithm

import trigonometric.pow
import kotlin.math.ln

class LnFunc(
    private val ln: Ln = Ln(),
    private val log2: LogarithmFunction = LnBase(2.0, ln),
    private val log3: LogarithmFunction = LnBase(3.0, ln),
    private val log5: LogarithmFunction = LnBase(5.0, ln),
    private val log10: LogarithmFunction = LnBase(10.0, ln)
) {
    fun calc(x: Double, eps: Double = 1e-10): Double {
        require(eps > 0) { "epsilon must be positive" }
        require(x > 0) { "x must be positive" }

        return (((((ln.calc(x, eps) * log2.calc(x, eps)) - log3.calc(x, eps)) + log3.calc(
            x,
            eps
        )) - ((log2.calc(x, eps) + log10.calc(x, eps)) pow 3)) / ((log10.calc(
            x,
            eps
        ) + (log5.calc(x, eps) - ln.calc(x, eps))) / ((ln.calc(x, eps) pow 2) + log2.calc(x, eps))))

    }
}