package part3.locations

import part3.entities.Entity
import part3.Planet

data class Catalog(
    private val entities: MutableList<Entity> = mutableListOf(),
    private val planets: MutableList<Planet> = mutableListOf()
) : Location(entities, mutableListOf()) {

    fun addPlanet(planet: Planet) {
        if(!planets.contains(planet))
            planets.addLast(planet)
    }

    fun removePlanet(): Planet? {
        return planets.firstOrNull()?.let {
            planets.remove(it)
            it
        }
    }

    fun isHavePlanet(): Boolean {
        return planets.isNotEmpty()
    }

    fun planets(): List<Planet> = planets
}