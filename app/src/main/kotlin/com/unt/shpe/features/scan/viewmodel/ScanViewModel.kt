package com.unt.shpe.features.scan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unt.shpe.features.events.model.SampleEvents
import com.unt.shpe.features.events.model.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for QR code scanning and attendance submission.
 * Manages scanner state, event selection, and attendance processing.
 * Maps 1:1 with iOS ScanViewModel.
 */
class ScanViewModel : ViewModel() {
    private val _selectedEvent = MutableStateFlow(SampleEvents.samples[0])
    val selectedEvent: StateFlow<Event> = _selectedEvent.asStateFlow()

    private val _isShowingSuccess = MutableStateFlow(false)
    val isShowingSuccess: StateFlow<Boolean> = _isShowingSuccess.asStateFlow()

    private val _isProcessing = MutableStateFlow(false)
    val isProcessing: StateFlow<Boolean> = _isProcessing.asStateFlow()

    fun selectEvent(event: Event) {
        _selectedEvent.value = event
    }

    fun submitDemoScan() {
        viewModelScope.launch {
            if (_isProcessing.value) return@launch
            
            _isProcessing.value = true
            _isShowingSuccess.value = true
            
            // Backend submission would happen here
            
            _isProcessing.value = false
        }
    }

    fun resetSuccess() {
        _isShowingSuccess.value = false
    }
}
