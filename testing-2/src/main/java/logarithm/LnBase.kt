package logarithm

class LnBase(
    private val base: Double,
    private val lnCalculator: LogarithmFunction = Ln(),
) : LogarithmFunction {
    override fun calc(x: Double, eps: Double): Double {
        require(x > 0) { "x must be a positive number" }
        require(base > 0 && base != 1.0) { "base must be a positive number and not equal to 1" }
        require(eps > 0) { "eps must be a positive number" }

        if (x == 1.0) return 0.0
        if (x == base) return 1.0

        val lnX = lnCalculator.calc(x, eps)
        val lnBase = lnCalculator.calc(base, eps)

        return lnX / lnBase
    }
}