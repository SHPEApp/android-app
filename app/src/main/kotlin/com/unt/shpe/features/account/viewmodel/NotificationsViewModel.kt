package com.unt.shpe.features.account.viewmodel

import androidx.lifecycle.ViewModel
import com.unt.shpe.features.account.model.AccountNotification
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel for notifications feature.
 * Manages notification list and read status.
 * Maps 1:1 with iOS NotificationsViewModel.
 */
class NotificationsViewModel : ViewModel() {
    private val _notifications = MutableStateFlow(emptyList<AccountNotification>())
    val notifications: StateFlow<List<AccountNotification>> = _notifications.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun loadNotifications() {
        _isLoading.value = true
        // Load from backend
        _isLoading.value = false
    }

    fun markAsRead(notificationId: String) {
        // Update read status
    }
}
