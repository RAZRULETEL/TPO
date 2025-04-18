package part3

import part3.humanoids.Magrathean
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.*

class MagratheanTest {

    @Test
    fun `Height must be positive`() {
        assertThrows<IllegalArgumentException> {
            Magrathean(-5)
        }
        assertThrows<IllegalArgumentException> {
            Magrathean(0)
        }
    }

    @Test
    fun `Valid height is accepted`() {
        val magrathean = Magrathean(160)
        assertEquals(160, magrathean.height)
    }

    @Test
    fun `isTall returns true for height more or equal to 150`() {
        assertTrue(Magrathean(150).isTall())
        assertTrue(Magrathean(200).isTall())
    }

    @Test
    fun `isTall returns false for height less than 150`() {
        assertFalse(Magrathean(149).isTall())
        assertFalse(Magrathean(100).isTall())
    }

    @Test
    fun `Inherits interaction capabilities from Humanoid`() {
        val magrathean = Magrathean(160)
        val interactable = TestInteractable()

        magrathean.startInteraction(interactable)
        assertTrue(magrathean.getInteractions().contains(interactable))

        magrathean.stopInteraction(interactable)
        assertFalse(magrathean.getInteractions().contains(interactable))
    }

    private class TestInteractable : Interactable {
        override fun startInteraction(inter: Interactable) {}
        override fun stopInteraction(inter: Interactable) {}
        override fun getInteractions(): List<Interactable> = emptyList()
    }
}