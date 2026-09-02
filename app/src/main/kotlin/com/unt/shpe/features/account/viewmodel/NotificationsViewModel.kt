package com.unt.shpe.features.account.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unt.shpe.features.account.model.AccountNotification
import com.unt.shpe.features.account.repository.NotificationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NotificationsViewModel(
    private val repository: NotificationRepository = NotificationRepository()
) : ViewModel() {
    enum class State {
        IDLE,
        LOADING,
        LOADED,
        FAILED
    }

    private val _notifications = MutableStateFlow(emptyList<AccountNotification>())
    val notifications: StateFlow<List<AccountNotification>> = _notifications.asStateFlow()

    private val _state = MutableStateFlow(State.IDLE)
    val state: StateFlow<State> = _state.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    val unreadCount: Int
        get() = _notifications.value.count { !it.isRead }

    fun load() {
        viewModelScope.launch {
            _state.value = State.LOADING
            _errorMessage.value = null

            try {
                repository.fetchNotifications().collect {
                    _notifications.value = it
                    _state.value = State.LOADED
                }
            } catch (e: Exception) {
                _state.value = State.FAILED
                _errorMessage.value = "Notifications are unavailable right now."
            }
        }
    }

    fun markAsRead(notification: AccountNotification) {
        viewModelScope.launch {
            val currentList = _notifications.value.toMutableList()
            val index = currentList.indexOfFirst { it.id == notification.id }
            if (index != -1 && !currentList[index].isRead) {
                try {
                    repository.markAsRead(notification.id)
                    currentList[index] = currentList[index].copy(isRead = true)
                    _notifications.value = currentList
                } catch (e: Exception) {
                    _errorMessage.value = "We could not update this notification."
                }
            }
        }
    }
}
