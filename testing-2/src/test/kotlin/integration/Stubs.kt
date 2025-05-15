package integration

import CSVTools
import logarithm.Ln
import logarithm.LnBase
import logarithm.LogarithmFunction
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import trigonometric.Cos
import trigonometric.Cot
import trigonometric.Csc
import trigonometric.Sec
import trigonometric.Sin
import trigonometric.Tan
import trigonometric.TrigonometricFunction
import kotlin.math.PI
import kotlin.math.abs

object Stubs {
    val stubSin = mock<Sin>()
    val stubCos = mock<Cos>()
    val stubTan = mock<Tan>()
    val stubCot = mock<Cot>()
    val stubSec = mock<Sec>()
    val stubCsc = mock<Csc>()

    val stubLn = mock<Ln>()
    val stubLog2 = mock<LnBase>()
    val stubLog3 = mock<LnBase>()
    val stubLog5 = mock<LnBase>()
    val stubLog10 = mock<LnBase>()

    init {
        tabulateTrigStub(stubSin, "sin.csv")
        tabulateTrigStub(stubCos, "cos.csv")
        tabulateTrigStub(stubTan, "tan.csv")
        tabulateTrigStub(stubCot, "cot.csv")
        tabulateTrigStub(stubSec, "sec.csv")
        tabulateTrigStub(stubCsc, "csc.csv")

        tabulateLnStub(stubLn, "ln.csv")
        tabulateLnStub(stubLog2, "log2.csv")
        tabulateLnStub(stubLog3, "log3.csv")
        tabulateLnStub(stubLog5, "log5.csv")
        tabulateLnStub(stubLog10, "log10.csv")
    }

    private fun tabulateTrigStub(func: TrigonometricFunction, fileName: String) {
        val funcTable = CSVTools.loadFromCSVFile(fileName);

        whenever(func.calc(any(), any())).thenAnswer { invocation ->
            val x = invocation.arguments[0] as Double
            funcTable.getValue(x) ?: throw IllegalArgumentException("x out of range")
        }
    }

    private fun tabulateLnStub(func: LogarithmFunction, fileName: String) {
        val funcTable = CSVTools.loadFromCSVFile(fileName);

        whenever(func.calc(any(), any())).thenAnswer { invocation ->
            val x = invocation.arguments[0] as Double
            require(x > 0) { "Logarithm is undefined for non-positive values" }
            funcTable.getValue(x) ?: throw IllegalArgumentException("x out of range")
        }
    }
}