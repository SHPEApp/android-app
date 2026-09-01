package com.unt.shpe.features.account.service

import android.content.Context
import androidx.core.app.NotificationManagerCompat

/**
 * Service for managing notification permissions.
 * Handles runtime permission requests and status checks.
 * Maps 1:1 with iOS NotificationPermissionService.
 */
class NotificationPermissionService(private val context: Context) {
    fun areNotificationsEnabled(): Boolean {
        return NotificationManagerCompat.from(context).areNotificationsEnabled()
    }

    fun requestNotificationPermission() {
        // Request runtime permission - requires Android 13+
        // Implementation depends on app's minimum SDK level
    }
}
