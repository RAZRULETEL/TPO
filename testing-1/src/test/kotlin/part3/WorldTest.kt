package part3

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import part3.entities.Entity
import part3.entities.PlushFurniture
import part3.enums.Material
import part3.humanoids.Magrathean
import part3.locations.Catalog
import part3.locations.Location
import kotlin.test.*

class WorldTest {

    private lateinit var world: World
    private lateinit var magrathean: Magrathean
    private lateinit var catalog: Catalog
    private lateinit var location: Location

    @BeforeEach
    fun setUp() {
        world = World()
        magrathean = Magrathean(160)
        catalog = Catalog(planets = mutableListOf(Planet(true)))
        location = Location(mutableListOf(), mutableListOf())
    }

    // Planet Destruction Tests
    @Test
    fun `destroying last planet switches to plush location`() {
        world.processEvent(Event.PlanetDestroyed)

        val newLocation = world.location() as Location
        val entity = newLocation.getEntity(0)
        assertNotNull(entity)
        assertTrue(entity is PlushFurniture)
        assertEquals(9, (entity as PlushFurniture).comfortLevel)
        assertNull(newLocation.getEntity(1)) // Verify only one entity exists
    }

    @Test
    fun `destroying non-last planet leaves catalog`() {
        catalog = Catalog(planets = mutableListOf(Planet(true), Planet(true)))
        world = World(catalog)

        world.processEvent(Event.PlanetDestroyed)

        assertTrue(world.location() is Catalog)
        assertEquals(1, (world.location() as Catalog).planets().size)
    }

    // Magrathean Entrance Tests
    @Test
    fun `entering Magrathean in catalog throws exception`() {
        assertFailsWith<IllegalArgumentException> {
            world.processEvent(Event.MagratheanEntered(magrathean))
        }
    }

    @Test
    fun `Magrathean interacts with first plush after index 0`() {
        val plush = PlushFurniture(5)
        location.addEntity(TestEntity()) // Index 0
        location.addEntity(plush)        // Index 1
        world = World(location)

        world.processEvent(Event.MagratheanEntered(magrathean))

        assertTrue(plush.getInteractions().contains(magrathean))
        assertEquals(1, location.getHumanoidsCount())
    }

    @Test
    fun `Magrathean skips plush at index 0`() {
        val plush = PlushFurniture(5)
        location.addEntity(plush) // Index 0
        world = World(location)

        world.processEvent(Event.MagratheanEntered(magrathean))

        assertFalse(plush.getInteractions().contains(magrathean))
        assertEquals(1, location.getHumanoidsCount())
    }

    @Test
    fun `Magrathean adds to humanoids with no interaction`() {
        location.addEntity(PlushFurniture(9))
        world = World(location)

        world.processEvent(Event.MagratheanEntered(magrathean))

        assertEquals(1, location.getHumanoidsCount())
        val entity = location.getEntity(0)
        assertNotNull(entity)
        assertTrue(entity is Interactable)
        assertTrue(entity.getInteractions().isEmpty())
    }

    @Test
    fun `Magrathean interacts with multiple entities`() {
        val plush1 = PlushFurniture(5)
        val plush2 = PlushFurniture(6)
        location.addEntity(TestEntity()) // 0
        location.addEntity(plush1)       // 1
        location.addEntity(TestEntity()) // 2
        location.addEntity(plush2)       // 3
        world = World(location)

        world.processEvent(Event.MagratheanEntered(magrathean))

        assertTrue(plush1.getInteractions().contains(magrathean))
        assertFalse(plush2.getInteractions().contains(magrathean))
    }

    // Helper classes
    private class TestEntity : Entity() {
        override val material = Material.METAL
    }
}