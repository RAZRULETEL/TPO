package part3

interface Interactable {
    fun startInteraction(inter: Interactable);
    fun stopInteraction(inter: Interactable);
    fun getInteractions(): List<Interactable>;
}