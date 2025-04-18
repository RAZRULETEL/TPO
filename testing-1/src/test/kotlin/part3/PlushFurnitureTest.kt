package part3.entities

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Test
import part3.Interactable
import part3.enums.Material
import part3.humanoids.Humanoid
import kotlin.test.*

class PlushFurnitureTest {

    private lateinit var plushFurniture: PlushFurniture
    private lateinit var humanoid: Humanoid

    @BeforeEach
    fun setUp() {
        plushFurniture = PlushFurniture(7)
        humanoid = Humanoid()
    }

    @Test
    fun `Material should be PLUSH`() {
        assertEquals(Material.PLUSH, plushFurniture.material)
    }

    @Test
    fun `Comfort level initializes correctly`() {
        assertEquals(7, plushFurniture.comfortLevel)
        val otherPlush = PlushFurniture(3)
        assertEquals(3, otherPlush.comfortLevel)
    }

    @Test
    fun `Start interaction adds to both interaction lists`() {
        plushFurniture.startInteraction(humanoid)

        assertTrue(plushFurniture.getInteractions().contains(humanoid))
        assertTrue(humanoid.getInteractions().contains(plushFurniture))
    }

    @Test
    fun `Start interaction throws exception for non-humanoid`() {
        val nonHumanoid = object : Interactable {
            override fun startInteraction(inter: Interactable) {}
            override fun stopInteraction(inter: Interactable) {}
            override fun getInteractions(): List<Interactable> = emptyList()
        }

        assertFailsWith<IllegalArgumentException> {
            plushFurniture.startInteraction(nonHumanoid)
        }
    }

    @Test
    fun `Start interaction throws exception when already interacting`() {
        plushFurniture.startInteraction(humanoid)

        assertFailsWith<IllegalArgumentException> {
            plushFurniture.startInteraction(humanoid)
        }
    }

    @Test
    fun `Stop interaction removes from both lists`() {
        plushFurniture.startInteraction(humanoid)
        plushFurniture.stopInteraction(humanoid)

        assertFalse(plushFurniture.getInteractions().contains(humanoid))
        assertFalse(humanoid.getInteractions().contains(plushFurniture))
    }

    @Test
    fun `isComfortable returns true when comfortLevel more or equal to 5`() {
        assertTrue(plushFurniture.isComfortable())
        assertFalse(PlushFurniture(4).isComfortable())
    }

    @Test
    fun `Interaction lists are independent between different instances`() {
        val plush2 = PlushFurniture(5)
        plushFurniture.startInteraction(humanoid)
        plush2.startInteraction(humanoid)

        assertEquals(1, plushFurniture.getInteractions().size)
        assertEquals(1, plush2.getInteractions().size)
        assertEquals(2, humanoid.getInteractions().size)
    }

    @Test
    fun `Humanoid already interacting with plush`() {
        humanoid.startInteraction(plushFurniture)
        plushFurniture.startInteraction(humanoid)
        assertEquals(1, plushFurniture.getInteractions().size)
        assertEquals(1, humanoid.getInteractions().size)
    }
}