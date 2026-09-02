package com.unt.shpe.features.account.repository

import com.unt.shpe.features.account.model.AccountNotification
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class NotificationRepository {
    suspend fun fetchNotifications(): Flow<List<AccountNotification>> {
        // Fetch from backend
        return flowOf(emptyList())
    }

    suspend fun markAsRead(notificationId: String) {
        // Update on backend
    }
}
