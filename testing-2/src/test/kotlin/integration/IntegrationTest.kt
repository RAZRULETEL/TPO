package integration

import logarithm.Ln
import logarithm.LnBase
import logarithm.LnFunc
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import Func
import trigonometric.Cos
import trigonometric.Cot
import trigonometric.Csc
import trigonometric.Sec
import trigonometric.Sin
import trigonometric.Tan
import trigonometric.TrigFunc


class IntegrationTest {

    @ParameterizedTest(name = "Test trigonometric function with x = {0}, expected = {1}")
    @CsvSource("-3.72326, 0, 1.0",
        "-2.34295,2.85452, 1.0",
        "-3.89396, -576.94486, 1.0",
        "-3.88636,-576.15167, 1.0",
        "-3.94131,1.04617, 1.0",
        "-3.94, -0.29423, 1.0",
        "-3.9383, -0.14438, 1.0",
        "-3.93635, -0.28241, 1.0",
        "-3.91503, -278.45888, 1.0",
        "-3.85804, -277.28292, 1.0",
        "-3.8047,-3.6132, 1.0",
        "-3.7602,-0.00015, 1.0",
        "-3.6862,-0.44226, 1.0",
        "-3.6769, -74.19852, 1.0",
        "-5.70152, 0, 1.0",
        "-5.6665, 0.00149, 1.0",
        "-5.6388, 4.70214, 1.0",
        "-5.626, 45.39168, 1.0",
        "-5.7304,0.02447, 1.0",
        "-5.74164,30.443, 1.0",
        "-2.34218, -7.6039, 1.0",
        "-0.884143, -1907438.77003, 3000.0",
        "-0.9456, -2493708.38808, 3000.0",
        "-0.8276, -2492912.95687, 3000.0",
        "-2.276732, 344577.93368, 3000.0",
        "-2.28957, 393497.2927, 3000.0",
        "-2.25458, 392659.28775, 3000.0",
        "-3.99635, -106476.70816, 3000.0",
        "-4.03, -184850.71683, 3000.0",
        "-3.98368, -213126.56806, 3000.0",
    )
    fun testTrigFuncWithTableValues(x: Double, expected: Double, prec: Double = 1.0) {
        val sin = Stubs.stubSin
        val cos = Stubs.stubCos
        val cot = Stubs.stubCot
        val csc = Stubs.stubCsc
        val sec = Stubs.stubSec
        val tan = Stubs.stubTan

        val trigonometricFunction =
            TrigFunc(sin = sin, cos = cos, cot = cot, csc = csc, sec = sec, tan = tan)
        val result = trigonometricFunction.calc(x, 1e-6)

        assertEquals(expected, result, prec, "Trigonometric function failed for x = $x")
    }

    @ParameterizedTest(name = "Test logarithmic function with x = {0}, expected = {1}")
    @CsvSource(
        "0.344375, 65.34417",
        "0.2863, 52.43734",
        "0.497, 39.6338",
        "0.971, 0.03602",
        "1,NaN",
        "1.159081, 0.290625",
        "1.1261, 0.260237",
        "1.031, 0.030627",
        "1.0773, 0.143687",
        "1.1911, 0.254563",
        "1.243795, 0",
        "0.23629, 0",
    )
    fun testLnFunc(input: Double, expected: Double) {
        val log2 = Stubs.stubLog2
        val log5 = Stubs.stubLog5
        val log10 = Stubs.stubLog10
        val ln = Stubs.stubLn


        val logorithmicFunction = LnFunc(log2 = log2, log5 = log5, log10 = log10, ln = ln)
        val result = logorithmicFunction.calc(input, 1e-10)
        kotlin.test.assertEquals(expected, result, 1e-1)
    }

    @ParameterizedTest(name = "Test full function with all mocks x = {0}, expected = {1}")
    @CsvSource(
        "-3.72326, 0, 1.0",
        "-2.34295,2.85452, 1.0",
        "-3.89396, -576.94486, 1.0",
        "-3.88636,-576.15167, 1.0",
        "-3.94131,1.04617, 1.0",
        "-3.94, -0.29423, 1.0",
        "-3.9383, -0.14438, 1.0",
        "-3.93635, -0.28241, 1.0",
        "-3.91503, -278.45888, 1.0",
        "-3.85804, -277.28292, 1.0",
        "-3.8047,-3.6132, 1.0",
        "-3.7602,-0.00015, 1.0",
        "-3.6862,-0.44226, 1.0",
        "-3.6769, -74.19852, 1.0",
        "-5.70152, 0, 1.0",
        "-5.6665, 0.00149, 1.0",
        "-5.6388, 4.70214, 1.0",
        "-5.626, 45.39168, 1.0",
        "-5.7304,0.02447, 1.0",
        "-5.74164,30.443, 1.0",
        "-2.34218, -7.6039, 1.0",
        "-0.884143, -1907438.77003, 3000.0",
        "-0.9456, -2493708.38808, 3000.0",
        "-0.8276, -2492912.95687, 3000.0",
        "-2.276732, 344577.93368, 3000.0",
        "-2.28957, 393497.2927, 3000.0",
        "-2.25458, 392659.28775, 3000.0",
        "-3.99635, -106476.70816, 3000.0",
        "-4.03, -184850.71683, 3000.0",
        "-3.98368, -213126.56806, 3000.0",
        "0.344375, 65.34417, 1.0",
        "0.2863, 52.43734, 1.0",
        "0.497, 39.6338, 1.0",
        "0.971, 0.03602, 1.0",
        "1,NaN, 1.0",
        "1.159081, 0.290625, 1.0",
        "1.1261, 0.260237, 1.0",
        "1.031, 0.030627, 1.0",
        "1.0773, 0.143687, 1.0",
        "1.1911, 0.254563, 1.0",
        "1.243795, 0, 1.0",
        "0.23629, 0, 1.0",
    )

    fun testFullFunctionAllMocks(x: Double, expected: Double, prec: Double = 1.0) {
        val sin = Stubs.stubSin
        val cos = Stubs.stubCos
        val cot = Stubs.stubCot
        val csc = Stubs.stubCsc
        val sec = Stubs.stubSec
        val tan = Stubs.stubTan

        val trigonometricFunction =
            TrigFunc(sin = sin, cos = cos, cot = cot, csc = csc, sec = sec, tan = tan)

        val log2 = Stubs.stubLog2
        val log5 = Stubs.stubLog5
        val log10 = Stubs.stubLog10
        val ln = Stubs.stubLn

        val logorithmicFunction = LnFunc(log2 = log2, log5 = log5, log10 = log10, ln = ln)

        var result = Func(trigonometricFunction, logorithmicFunction).calc(x, 1e-10)

        assertEquals(expected, result, prec)
    }

    @ParameterizedTest(name = "Test full function on first level x = {0}, expected = {1}")
    @CsvSource(
        "-3.72326, 0, 1.0",
        "-2.34295,2.85452, 1.0",
        "-3.89396, -576.94486, 1.0",
        "-3.88636,-576.15167, 1.0",
        "-3.94131,1.04617, 1.0",
        "-3.94, -0.29423, 1.0",
        "-3.9383, -0.14438, 1.0",
        "-3.93635, -0.28241, 1.0",
        "-3.91503, -278.45888, 1.0",
        "-3.85804, -277.28292, 1.0",
        "-3.8047,-3.6132, 1.0",
        "-3.7602,-0.00015, 1.0",
        "-3.6862,-0.44226, 1.0",
        "-3.6769, -74.19852, 1.0",
        "-5.70152, 0, 1.0",
        "-5.6665, 0.00149, 1.0",
        "-5.6388, 4.70214, 1.0",
        "-5.626, 45.39168, 1.0",
        "-5.7304,0.02447, 1.0",
        "-5.74164,30.443, 1.0",
        "-2.34218, -7.6039, 1.0",
        "-0.884143, -1907438.77003, 3000.0",
        "-0.9456, -2493708.38808, 3000.0",
        "-0.8276, -2492912.95687, 3000.0",
        "-2.276732, 344577.93368, 3000.0",
        "-2.28957, 393497.2927, 3000.0",
        "-2.25458, 392659.28775, 3000.0",
        "-3.99635, -106476.70816, 3000.0",
        "-4.03, -184850.71683, 3000.0",
        "-3.98368, -213126.56806, 3000.0",
        "0.344375, 65.34417, 1.0",
        "0.2863, 52.43734, 1.0",
        "0.497, 39.6338, 1.0",
        "0.971, 0.03602, 1.0",
        "1,NaN, 1.0",
        "1.159081, 0.290625, 1.0",
        "1.1261, 0.260237, 1.0",
        "1.031, 0.030627, 1.0",
        "1.0773, 0.143687, 1.0",
        "1.1911, 0.254563, 1.0",
        "1.243795, 0, 1.0",
        "0.23629, 0, 1.0",
    )

    fun testFullFunctionFirstLevel(x: Double, expected: Double, prec: Double = 1.0) {
        val sin = Stubs.stubSin
        val cos = Stubs.stubCos
        val cot = Cot()
        val csc = Stubs.stubCsc
        val sec = Sec()
        val tan = Tan()

        val trigonometricFunction =
            TrigFunc(sin = sin, cos = cos, cot = cot, csc = csc, sec = sec, tan = tan)

        val log2 = LnBase( 2.0)
        val log3 = LnBase( 3.0)
        val log5 = LnBase( 5.0)
        val log10 = LnBase( 10.0)
        val ln = Ln()

        val logarithmicFunction = LnFunc(log2 = log2, log3 = log3, log5 = log5, log10 = log10, ln = ln)

        val result = Func(trigonometricFunction, logarithmicFunction).calc(x, 1e-10)

        assertEquals(expected, result, prec)
    }

    @ParameterizedTest(name = "Test full function on second level x = {0}, expected = {1}")
    @CsvSource(
        "-3.72326, 0, 1.0",
        "-2.34295,2.85452, 1.0",
        "-3.89396, -576.94486, 1.0",
        "-3.88636,-576.15167, 1.0",
        "-3.94131,1.04617, 1.0",
        "-3.94, -0.29423, 1.0",
        "-3.9383, -0.14438, 1.0",
        "-3.93635, -0.28241, 1.0",
        "-3.91503, -278.45888, 1.0",
        "-3.85804, -277.28292, 1.0",
        "-3.8047,-3.6132, 1.0",
        "-3.7602,-0.00015, 1.0",
        "-3.6862,-0.44226, 1.0",
        "-3.6769, -74.19852, 1.0",
        "-5.70152, 0, 1.0",
        "-5.6665, 0.00149, 1.0",
        "-5.6388, 4.70214, 1.0",
        "-5.626, 45.39168, 1.0",
        "-5.7304,0.02447, 1.0",
        "-5.74164,30.443, 1.0",
        "-2.34218, -7.6039, 1.0",
        "-0.884143, -1907438.77003, 3000.0",
        "-0.9456, -2493708.38808, 3000.0",
        "-0.8276, -2492912.95687, 3000.0",
        "-2.276732, 344577.93368, 3000.0",
        "-2.28957, 393497.2927, 3000.0",
        "-2.25458, 392659.28775, 3000.0",
        "-3.99635, -106476.70816, 3000.0",
        "-4.03, -184850.71683, 3000.0",
        "-3.98368, -213126.56806, 3000.0",
        "0.344375, 65.34417, 1.0",
        "0.2863, 52.43734, 1.0",
        "0.497, 39.6338, 1.0",
        "0.971, 0.03602, 1.0",
        "1,NaN, 1.0",
        "1.159081, 0.290625, 1.0",
        "1.1261, 0.260237, 1.0",
        "1.031, 0.030627, 1.0",
        "1.0773, 0.143687, 1.0",
        "1.1911, 0.254563, 1.0",
        "1.243795, 0, 1.0",
        "0.23629, 0, 1.0",
    )

    fun testFullFunctionSecondLevel(x: Double, expected: Double, prec: Double = 1.0) {
        val sin = Stubs.stubSin
        val cos = Cos()
        val cot = Cot()
        val csc = Csc()
        val sec = Sec()
        val tan = Tan()

        val trigonometricFunction =
            TrigFunc(sin = sin, cos = cos, cot = cot, csc = csc, sec = sec, tan = tan)

        val log2 = LnBase( 2.0)
        val log3 = LnBase( 3.0)
        val log5 = LnBase( 5.0)
        val log10 = LnBase( 10.0)
        val ln = Ln()

        val logarithmicFunction = LnFunc(log2 = log2, log3 = log3, log5 = log5, log10 = log10, ln = ln)

        val result = Func(trigonometricFunction, logarithmicFunction).calc(x, 1e-10)

        assertEquals(expected, result, prec)
    }

    @ParameterizedTest(name = "Test full function on third level x = {0}, expected = {1}")
    @CsvSource(
        "-3.72326, 0, 1.0",
        "-2.34295,2.85452, 1.0",
        "-3.89396, -576.94486, 1.0",
        "-3.88636,-576.15167, 1.0",
        "-3.94131,1.04617, 1.0",
        "-3.94, -0.29423, 1.0",
        "-3.9383, -0.14438, 1.0",
        "-3.93635, -0.28241, 1.0",
        "-3.91503, -278.45888, 1.0",
        "-3.85804, -277.28292, 1.0",
        "-3.8047,-3.6132, 1.0",
        "-3.7602,-0.00015, 1.0",
        "-3.6862,-0.44226, 1.0",
        "-3.6769, -74.19852, 1.0",
        "-5.70152, 0, 1.0",
        "-5.6665, 0.00149, 1.0",
        "-5.6388, 4.70214, 1.0",
        "-5.626, 45.39168, 1.0",
        "-5.7304,0.02447, 1.0",
        "-5.74164,30.443, 1.0",
        "-2.34218, -7.6039, 1.0",
        "-0.884143, -1907438.77003, 3000.0",
        "-0.9456, -2493708.38808, 3000.0",
        "-0.8276, -2492912.95687, 3000.0",
        "-2.276732, 344577.93368, 3000.0",
        "-2.28957, 393497.2927, 3000.0",
        "-2.25458, 392659.28775, 3000.0",
        "-3.99635, -106476.70816, 3000.0",
        "-4.03, -184850.71683, 3000.0",
        "-3.98368, -213126.56806, 3000.0",
        "0.344375, 65.34417, 1.0",
        "0.2863, 52.43734, 1.0",
        "0.497, 39.6338, 1.0",
        "0.971, 0.03602, 1.0",
        "1,NaN, 1.0",
        "1.159081, 0.290625, 1.0",
        "1.1261, 0.260237, 1.0",
        "1.031, 0.030627, 1.0",
        "1.0773, 0.143687, 1.0",
        "1.1911, 0.254563, 1.0",
        "1.243795, 0, 1.0",
        "0.23629, 0, 1.0",
    )

    fun testFullFunctionThirdLevel(x: Double, expected: Double, prec: Double = 1.0) {
        val sin = Sin()
        val cos = Cos()
        val cot = Cot()
        val csc = Csc()
        val sec = Sec()
        val tan = Tan()

        val trigonometricFunction =
            TrigFunc(sin = sin, cos = cos, cot = cot, csc = csc, sec = sec, tan = tan)

        val log2 = LnBase( 2.0)
        val log3 = LnBase( 3.0)
        val log5 = LnBase( 5.0)
        val log10 = LnBase( 10.0)
        val ln = Ln()

        val logarithmicFunction = LnFunc(log2 = log2, log3 = log3, log5 = log5, log10 = log10, ln = ln)

        val result = Func(trigonometricFunction, logarithmicFunction).calc(x, 1e-10)

        assertEquals(expected, result, prec)
    }

    ///// LOGARITHMIC FUNCTIONS

    @ParameterizedTest(name = "Test full function on fist level logaritmic x = {0}, expected = {1}")
    @CsvSource(
        "0.344375, 65.34417",
        "0.2863, 52.43734",
        "0.497, 39.6338",
        "0.971, 0.03602",
        "1,NaN",
        "1.159081, 0.290625",
        "1.1261, 0.260237",
        "1.031, 0.030627",
        "1.0773, 0.143687",
        "1.1911, 0.254563",
        "1.243795, 0",
        "0.23629, 0",
    )

    fun testFullFunctionFirstLevelLogarithmic(x: Double, expected: Double) {
        val sin = Sin()
        val cos = Cos()
        val cot = Cot()
        val csc = Csc()
        val sec = Sec()
        val tan = Tan()

        val trigonometricFunction =
            TrigFunc(sin = sin, cos = cos, cot = cot, csc = csc, sec = sec, tan = tan)

        val log2 = Stubs.stubLog2
        val log3 = Stubs.stubLog3
        val log5 = Stubs.stubLog5
        val log10 = Stubs.stubLog10
        val ln = Stubs.stubLn

        val logarithmicFunction = LnFunc(log2 = log2, log3 = log3, log5 = log5, log10 = log10, ln = ln)

        val result = Func(trigonometricFunction, logarithmicFunction).calc(x, 1e-10)

        assertEquals(expected, result, 1e-1)
    }

    @ParameterizedTest(name = "Test full function on second level logarithmic x = {0}, expected = {1}")
    @CsvSource(
        "0.344375, 65.34417",
        "0.2863, 52.43734",
        "0.497, 39.6338",
        "0.971, 0.03602",
        "1,NaN",
        "1.159081, 0.290625",
        "1.1261, 0.260237",
        "1.031, 0.030627",
        "1.0773, 0.143687",
        "1.1911, 0.254563",
        "1.243795, 0",
        "0.23629, 0",
    )

    fun testFullFunctionSecondLevelLogarithmic(x: Double, expected: Double) {
        val sin = Sin()
        val cos = Cos()
        val cot = Cot()
        val csc = Csc()
        val sec = Sec()
        val tan = Tan()

        val trigonometricFunction =
            TrigFunc(sin = sin, cos = cos, cot = cot, csc = csc, sec = sec, tan = tan)

        val log2 = LnBase( 2.0)
        val log3 = LnBase( 3.0)
        val log5 = LnBase( 5.0)
        val log10 = LnBase( 10.0)
        val ln = Stubs.stubLn

        val logarithmicFunction = LnFunc(log2 = log2, log3 = log3, log5 = log5, log10 = log10, ln = ln)

        val result = Func(trigonometricFunction, logarithmicFunction).calc(x, 1e-10)

        assertEquals(expected, result, 1e-1)
    }
}