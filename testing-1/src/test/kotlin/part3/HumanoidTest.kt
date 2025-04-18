package part3

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import part3.humanoids.Humanoid
import kotlin.test.*

class HumanoidTest {

    private lateinit var humanoid: Humanoid
    private lateinit var interactable1: Interactable
    private lateinit var interactable2: Interactable

    @BeforeEach
    fun setUp() {
        humanoid = Humanoid()
        interactable1 = TestInteractable()
        interactable2 = TestInteractable()
    }

    @Test
    fun `startInteraction adds interactable to list`() {
        humanoid.startInteraction(interactable1)
        assertTrue(humanoid.getInteractions().contains(interactable1))
    }

    @Test
    fun `startInteraction throws exception when adding duplicate`() {
        humanoid.startInteraction(interactable1)
        val exception = assertFailsWith<IllegalArgumentException> {
            humanoid.startInteraction(interactable1)
        }
        assertEquals("Entity already interacting with this entity", exception.message)
    }

    @Test
    fun `stopInteraction removes interactable from list`() {
        humanoid.startInteraction(interactable1)
        humanoid.stopInteraction(interactable1)
        assertFalse(humanoid.getInteractions().contains(interactable1))
    }

    @Test
    fun `stopInteraction handles non-existent interactable gracefully`() {
        humanoid.stopInteraction(interactable1) // Should not throw
        assertTrue(humanoid.getInteractions().isEmpty())
    }

    @Test
    fun `getInteractions returns current state`() {
        val interactions = humanoid.getInteractions()
        assertTrue(interactions.isEmpty())

        humanoid.startInteraction(interactable1)
        assertTrue(interactions.contains(interactable1))

        humanoid.startInteraction(interactable2)
        assertEquals(2, interactions.size)

        humanoid.stopInteraction(interactable1)
        assertFalse(interactions.contains(interactable1))
    }

    @Test
    fun `multiple interactions are tracked correctly`() {
        humanoid.startInteraction(interactable1)
        humanoid.startInteraction(interactable2)

        val interactions = humanoid.getInteractions()
        assertEquals(2, interactions.size)
        assertTrue(interactions.containsAll(listOf(interactable1, interactable2)))
    }

    @Test
    fun `interaction lists are instance-specific`() {
        val anotherHumanoid = Humanoid()
        humanoid.startInteraction(interactable1)
        anotherHumanoid.startInteraction(interactable2)

        assertNotEquals(humanoid.getInteractions(), anotherHumanoid.getInteractions())
        assertFalse(humanoid.getInteractions().contains(interactable2))
        assertFalse(anotherHumanoid.getInteractions().contains(interactable1))
    }

    // Test implementation of Interactable for mocking
    private class TestInteractable : Interactable {
        override fun startInteraction(inter: Interactable) {}
        override fun stopInteraction(inter: Interactable) {}
        override fun getInteractions(): List<Interactable> = emptyList()
    }
}