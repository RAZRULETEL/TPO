package part3.humanoids

class Magrathean(val height: Int) : Humanoid() {

    init {
        require(height > 0) { "Height must be a positive number" }
    }

    fun isTall(): Boolean = height >= 150
}