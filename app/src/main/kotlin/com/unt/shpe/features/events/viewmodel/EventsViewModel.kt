package com.unt.shpe.features.events.viewmodel

import androidx.lifecycle.ViewModel
import com.unt.shpe.features.events.model.Event
import com.unt.shpe.features.events.model.SampleEvents
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class EventsViewModel : ViewModel() {
    private val _events = MutableStateFlow(SampleEvents.samples)
    val events: StateFlow<List<Event>> = _events.asStateFlow()

    private val _selectedEvent = MutableStateFlow<Event?>(null)
    val selectedEvent: StateFlow<Event?> = _selectedEvent.asStateFlow()

    fun selectEvent(event: Event) {
        _selectedEvent.value = event
    }

    fun clearSelection() {
        _selectedEvent.value = null
    }
}
