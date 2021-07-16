package co.zimly.bytrain.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class Journey(val from: Station, val to: Station)

class JourneyViewModel : ViewModel() {
    val favorites = MutableLiveData(
        listOf(
            Journey(
                Station("WAT", "London Waterloo"),
                Station("RDG", "Reading"),
            ),
            Journey(
                Station("RDG", "Reading"),
                Station("WKM", "Wokingham"),
            ),
        )
    )

    val recents = MutableLiveData(
        listOf(
            Journey(
                Station("PAD", "London Paddington"),
                Station("RDG", "Reading"),
            ),
            Journey(
                Station("SUR", "Surbiton"),
                Station("GLD", "Guildford"),
            ),
        )
    )
}
