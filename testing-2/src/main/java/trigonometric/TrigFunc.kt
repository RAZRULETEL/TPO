package trigonometric

import kotlin.math.pow

class TrigFunc(
    private val sin: TrigonometricFunction = Sin(),
    private val cos: TrigonometricFunction = Cos(sin),
    private val tan: TrigonometricFunction = Tan(sin, cos),
    private val cot: TrigonometricFunction = Cot(sin, cos),
    private val sec: TrigonometricFunction = Sec(cos),
    private val csc: TrigonometricFunction = Csc(sin)
) {
    fun calc(x: Double, epsilon: Double = 1e-10): Double {
        require(epsilon > 0) { "epsilon must be positive" }

        val sinX = sin.calc(x, epsilon)
        val cosX = cos.calc(x, epsilon)
        val tanX = tan.calc(x, epsilon)
        val cotX = cot.calc(x, epsilon)
        val secX = sec.calc(x, epsilon)
        val cscX = csc.calc(x, epsilon)

        return (((((((((((((sinX / sinX) * (tanX * sinX)) - secX) pow 2) * cotX) pow 3) - secX) * cscX) pow 3) pow 3) pow 2) / (((((secX * cotX) * tanX) * sinX) pow 2) + (cosX / (((tanX / (cscX * (secX * (cotX * secX)))) + (((tanX pow 3) + (cosX / ((cscX - (cosX - cosX)) / cosX))) pow 3)) pow 2)))) * ((tanX + (((secX / (secX / cosX)) pow 2) * (cscX + sinX))) * (secX pow 2)))
    }
}

public infix fun Double.pow(power: Int): Double = this.pow(power.toDouble())