package part3.locations

import part3.entities.Entity
import part3.humanoids.Humanoid

open class Location(
    private val entities: MutableList<Entity> = mutableListOf(),
    private val humanoids: MutableList<Humanoid>
) {

    fun addEntity(entity: Entity) {
        if (!entities.contains(entity))
            entities.add(entity)
    }

    fun removeEntity(entity: Entity) {
        entities.remove(entity)
    }

    fun getEntity(index: Int): Entity? {
        return entities.getOrNull(index)
    }

    fun addHumanoid(entity: Humanoid) {
        if (!humanoids.contains(entity))
            humanoids.add(entity)
    }

    fun removeHumanoid(entity: Humanoid) {
        humanoids.remove(entity)
    }

    fun getHumanoid(index: Int): Humanoid? {
        return humanoids.getOrNull(index)
    }

    fun getHumanoidsCount(): Int = humanoids.size
}