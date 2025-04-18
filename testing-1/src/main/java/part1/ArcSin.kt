package part1

fun arcSinSeries(x: Double, n: Int): Double {
    require(x in -1.0..1.0) { "x must be in the range [-1, 1]" }

    var u = x
    var s = u
    for (k in 1..n + 1) {
        u *= x * x * (2 * k - 1) / (2 * k)
        s += u / (2 * k + 1)
    }

    return s
}
