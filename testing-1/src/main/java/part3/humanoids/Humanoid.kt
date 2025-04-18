package part3.humanoids

import part3.Interactable


open class Humanoid: Interactable {
    val interactList = mutableListOf<Interactable>()

    override fun startInteraction(inter: Interactable) {
        require(!interactList.contains(inter), {"Entity already interacting with this entity"})
        interactList.add(inter)
    }

    override fun stopInteraction(inter: Interactable) {
        interactList.remove(inter)
    }

    override fun getInteractions(): List<Interactable> {
        return interactList
    }
}