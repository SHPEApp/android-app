package com.unt.shpe.features.account.viewmodel

import androidx.lifecycle.ViewModel
import com.unt.shpe.features.account.service.NotificationPermissionService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NotificationSettingsViewModel(
    private val permissionService: NotificationPermissionService
) : ViewModel() {
    enum class PermissionState {
        UNKNOWN,
        NOT_DETERMINED,
        AUTHORIZED,
        DENIED
    }

    private val _eventReminders = MutableStateFlow(true)
    val eventReminders: StateFlow<Boolean> = _eventReminders.asStateFlow()

    private val _newsletterUpdates = MutableStateFlow(true)
    val newsletterUpdates: StateFlow<Boolean> = _newsletterUpdates.asStateFlow()

    private val _permissionState = MutableStateFlow(PermissionState.UNKNOWN)
    val permissionState: StateFlow<PermissionState> = _permissionState.asStateFlow()

    fun toggleEventReminders() {
        _eventReminders.value = !_eventReminders.value
    }

    fun toggleNewsletterUpdates() {
        _newsletterUpdates.value = !_newsletterUpdates.value
    }

    fun loadPermissionState() {
        val isEnabled = permissionService.areNotificationsEnabled()
        _permissionState.value = if (isEnabled) PermissionState.AUTHORIZED else PermissionState.DENIED
    }

    fun enableNotifications() {
        permissionService.requestNotificationPermission()
        loadPermissionState()
    }
}
