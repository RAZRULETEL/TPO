package part3

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import part3.entities.Entity
import part3.enums.Material
import part3.locations.Catalog

class CatalogTest {
    private lateinit var catalog: Catalog
    private val planet1 = Planet(inhabited = true)
    private val planet2 = Planet(inhabited = false)
    private val furniture = TestEntity()

    @BeforeEach
    fun setUp() {
        catalog = Catalog(entities = mutableListOf(furniture), planets = mutableListOf(planet1, planet2))
    }


    @Test
    fun `Remove first planet`() {
        assertTrue(catalog.removePlanet() == planet1)
        assertFalse(catalog.planets().contains(planet1))
        assertTrue(catalog.planets().contains(planet2))
        assertTrue(catalog.getEntity(0) == furniture)
    }

    @Test
    fun `Remove last planet`() {
        catalog = Catalog(planets = mutableListOf(planet1))
        assertTrue(catalog.removePlanet() == planet1)
        assertTrue(catalog.getEntity(0) == null)
    }

    @Test
    fun `Delete from empty catalog`() {
        catalog = Catalog()
        assertTrue(catalog.removePlanet() == null)
    }

    @Test
    fun `Remove planets only`() {
        catalog.removePlanet()
        assertTrue(catalog.getEntity(0) == furniture)
    }

    @Test
    fun `Check if have planet`() {
        assertTrue(catalog.isHavePlanet())
    }

    @Test
    fun `Check if don't have planet`() {
        catalog = Catalog()
        assertFalse(catalog.isHavePlanet())
    }

    @Test
    fun `Check deletion of all planets`() {
        catalog.removePlanet()
        catalog.removePlanet()
        assertFalse(catalog.isHavePlanet())
    }

    @Test
    fun `Add planet to catalog`() {
        catalog = Catalog()

        catalog.addPlanet(planet1)
        assertTrue(catalog.isHavePlanet())
        assertTrue(catalog.planets().contains(planet1))
        assertTrue(catalog.planets().size == 1)
    }

    @Test
    fun `Add planet duplicate to catalog`() {
        catalog = Catalog()

        catalog.addPlanet(planet1)
        catalog.addPlanet(planet1)
        assertTrue(catalog.isHavePlanet())
        assertTrue(catalog.planets().contains(planet1))
        assertTrue(catalog.planets().size == 1)
    }

    private class TestEntity(
        override val material: Material = Material.ORGANIC
    ) : Entity()
}