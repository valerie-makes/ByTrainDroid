package co.zimly.bytrain.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class Journey(val from: Station, val to: Station)

class JourneyViewModel : ViewModel() {
    private val _favorites = MutableLiveData(
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
    val favorites: LiveData<List<Journey>> = _favorites

    private val _recents = MutableLiveData(
        listOf(
            Journey(
                Station("PAD", "London Paddington"),
                Station("RDG", "Reading"),
            ),
            Journey(
                Station("SUR", "Surbiton"),
                Station("GLD", "Guildford"),
            ),
            Journey(
                Station("PAD", "London Paddington"),
                Station("SUR", "Surbiton"),
            ),
        )
    )
    val recents: LiveData<List<Journey>> = _recents
}
