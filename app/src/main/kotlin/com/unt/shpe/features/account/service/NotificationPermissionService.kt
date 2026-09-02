package com.unt.shpe.features.account.service

import android.content.Context
import androidx.core.app.NotificationManagerCompat

class NotificationPermissionService(private val context: Context) {
    fun areNotificationsEnabled(): Boolean {
        return NotificationManagerCompat.from(context).areNotificationsEnabled()
    }

    fun requestNotificationPermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            // Trigger POST_NOTIFICATIONS permission request
        }
    }
}
