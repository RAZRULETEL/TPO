package part3.entities

import part3.Interactable
import part3.enums.Material
import part3.humanoids.Humanoid

class PlushFurniture(
    val comfortLevel: Int
) : Interactable, Entity() {
    override val material: Material = Material.PLUSH
    val interactList = mutableListOf<Interactable>()

    override fun startInteraction(inter: Interactable) {
        require(inter is Humanoid, {"Only humanoids can sit on plush furniture"})
        require(!interactList.contains(inter)) { "Entity already interacting with this entity" }
        if (!inter.getInteractions().contains(this)) inter.startInteraction(this)
        interactList.add(inter)
    }

    override fun stopInteraction(inter: Interactable) {
        interactList.remove(inter)
        inter.stopInteraction(this)
    }

    override fun getInteractions(): List<Interactable> {
        return interactList
    }

    fun isComfortable(): Boolean = comfortLevel >= 5
}