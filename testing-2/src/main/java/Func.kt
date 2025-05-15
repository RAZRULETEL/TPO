import logarithm.LnFunc
import trigonometric.TrigFunc

class Func(
    private val trigonometricFunction: TrigFunc = TrigFunc(),
    private val logarithmicFunction: LnFunc = LnFunc()
) {
    fun calc(x: Double, epsilon: Double): Double {
        if (x <= 0) {
            return trigonometricFunction.calc(x, epsilon)
        }
        return logarithmicFunction.calc(x, epsilon)
    }
}