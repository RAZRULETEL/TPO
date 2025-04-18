package part3

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import part3.entities.PlushFurniture
import part3.humanoids.Humanoid
import part3.locations.Location
import kotlin.test.*

class LocationTest {

    private lateinit var location: Location
    private lateinit var entity1: PlushFurniture
    private lateinit var entity2: PlushFurniture
    private lateinit var humanoid1: Humanoid
    private lateinit var humanoid2: Humanoid

    @BeforeEach
    fun setUp() {
        location = Location(mutableListOf(), mutableListOf())
        entity1 = PlushFurniture(5)
        entity2 = PlushFurniture(6)
        humanoid1 = Humanoid()
        humanoid2 = Humanoid()
    }

    // Entity Tests
    @Test
    fun `addEntity adds new entity`() {
        location.addEntity(entity1)
        assertEquals(entity1, location.getEntity(0))
    }

    @Test
    fun `addEntity ignores duplicates`() {
        location.addEntity(entity1)
        location.addEntity(entity1)
        assertNull(location.getEntity(1))
    }

    @Test
    fun `removeEntity removes existing entity`() {
        location.addEntity(entity1)
        location.addEntity(entity2)
        location.removeEntity(entity1)
        assertEquals(entity2, location.getEntity(0))
        assertNull(location.getEntity(1))
    }

    @Test
    fun `removeEntity handles non-existent entity`() {
        location.addEntity(entity1)
        location.removeEntity(entity2)
        assertEquals(entity1, location.getEntity(0))
    }

    @Test
    fun `getEntity returns null for invalid index`() {
        assertNull(location.getEntity(-1))
        assertNull(location.getEntity(10))
    }

    // Humanoid Tests
    @Test
    fun `addHumanoid adds new humanoid`() {
        location.addHumanoid(humanoid1)
        assertEquals(1, location.getHumanoidsCount())
        assertEquals(humanoid1, location.getHumanoid(0))
    }

    @Test
    fun `addHumanoid ignores duplicates`() {
        location.addHumanoid(humanoid1)
        location.addHumanoid(humanoid1)
        assertEquals(1, location.getHumanoidsCount())
    }

    @Test
    fun `removeHumanoid removes existing humanoid`() {
        location.addHumanoid(humanoid1)
        location.addHumanoid(humanoid2)
        location.removeHumanoid(humanoid1)
        assertEquals(1, location.getHumanoidsCount())
        assertEquals(humanoid2, location.getHumanoid(0))
    }

    @Test
    fun `removeHumanoid handles non-existent humanoid`() {
        location.addHumanoid(humanoid1)
        location.removeHumanoid(humanoid2)
        assertEquals(1, location.getHumanoidsCount())
    }

    @Test
    fun `getHumanoid returns null for invalid index`() {
        assertNull(location.getHumanoid(-1))
        assertNull(location.getHumanoid(10))
    }

    @Test
    fun `getHumanoidsCount returns correct size`() {
        assertEquals(0, location.getHumanoidsCount())
        location.addHumanoid(humanoid1)
        assertEquals(1, location.getHumanoidsCount())
        location.removeHumanoid(humanoid1)
        assertEquals(0, location.getHumanoidsCount())
    }

    // Cross-functionality Tests
    @Test
    fun `entities and humanoids are managed independently`() {
        location.addEntity(entity1)
        location.addHumanoid(humanoid1)
        assertEquals(entity1, location.getEntity(0))
        assertEquals(humanoid1, location.getHumanoid(0))
        assertEquals(1, location.getHumanoidsCount())
    }
}