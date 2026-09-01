package com.unt.shpe.features.account.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel for notification settings.
 * Manages push notification preferences.
 * Maps 1:1 with iOS NotificationSettingsViewModel.
 */
class NotificationSettingsViewModel : ViewModel() {
    private val _eventRemindersEnabled = MutableStateFlow(true)
    val eventRemindersEnabled: StateFlow<Boolean> = _eventRemindersEnabled.asStateFlow()

    private val _newsletterUpdatesEnabled = MutableStateFlow(true)
    val newsletterUpdatesEnabled: StateFlow<Boolean> = _newsletterUpdatesEnabled.asStateFlow()

    fun toggleEventReminders() {
        _eventRemindersEnabled.value = !_eventRemindersEnabled.value
    }

    fun toggleNewsletterUpdates() {
        _newsletterUpdatesEnabled.value = !_newsletterUpdatesEnabled.value
    }
}
