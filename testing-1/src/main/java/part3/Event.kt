package part3

import part3.humanoids.Magrathean

sealed class Event {
    object PlanetDestroyed : Event()
    data class MagratheanEntered(val magrathean: Magrathean) : Event()
}