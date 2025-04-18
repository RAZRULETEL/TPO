package part3

import part3.humanoids.Magrathean
import part3.entities.PlushFurniture
import part3.locations.Catalog
import part3.locations.Location

class World(private var currentLocation: Location = Catalog(planets = mutableListOf(Planet(true)))) {

    fun processEvent(event: Event) {
        when (event) {
            is Event.MagratheanEntered -> {
                require(currentLocation !is Catalog, { "Can not enter magrathean in catalog" })
                handleMagratheanEntrance(event.magrathean)
            }
            else -> handlePlanetDestruction()
        }
    }

    private fun handlePlanetDestruction() {
        (currentLocation as Catalog).removePlanet()
        if (!(currentLocation as Catalog).isHavePlanet()) {
            currentLocation = Location(mutableListOf(), mutableListOf()).apply {
                addEntity(PlushFurniture(comfortLevel = 9))
            }
        }
    }

    private fun handleMagratheanEntrance(magrathean: Magrathean) {
        currentLocation.addHumanoid(magrathean)
        var i = 0
        while (currentLocation.getEntity(i) != null) {
            i++
            if (currentLocation.getEntity(i) is PlushFurniture) {
                (currentLocation.getEntity(i) as PlushFurniture).startInteraction(magrathean);
                break
            }
        };
    }

    fun location() = currentLocation
}