package com.unt.shpe.features.account.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.unt.shpe.app.TestTags
import com.unt.shpe.design.*
import com.unt.shpe.features.account.viewmodel.NotificationSettingsViewModel

@Composable
fun NotificationSettingsScreen(viewModel: NotificationSettingsViewModel) {
    val eventReminders by viewModel.eventReminders.collectAsState()
    val newsletterUpdates by viewModel.newsletterUpdates.collectAsState()
    val permissionState by viewModel.permissionState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadPermissionState()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brand.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SHPESectionTitle("NOTIFICATION PREFERENCES")

        SHPECard {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                PermissionBanner(
                    state = permissionState,
                    onEnable = viewModel::enableNotifications
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Event reminders",
                        modifier = Modifier.weight(1f)
                    )
                    Switch(
                        checked = eventReminders,
                        onCheckedChange = { viewModel.toggleEventReminders() },
                        modifier = Modifier.testTag(TestTags.Notifications.Settings.eventReminders)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Newsletter updates",
                        modifier = Modifier.weight(1f)
                    )
                    Switch(
                        checked = newsletterUpdates,
                        onCheckedChange = { viewModel.toggleNewsletterUpdates() },
                        modifier = Modifier.testTag(TestTags.Notifications.Settings.newsletterUpdates)
                    )
                }
            }
        }

        Text(
            text = "These preferences are stored on this device until the notification service and approved policy are connected.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun PermissionBanner(
    state: NotificationSettingsViewModel.PermissionState,
    onEnable: () -> Unit
) {
    when (state) {
        NotificationSettingsViewModel.PermissionState.AUTHORIZED -> {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.NotificationsActive,
                    contentDescription = null,
                    tint = Brand.green
                )
                Text(
                    text = "Notifications are enabled",
                    color = Brand.green,
                    modifier = Modifier.testTag(TestTags.Notifications.Settings.permissionStatus)
                )
            }
        }
        NotificationSettingsViewModel.PermissionState.DENIED -> {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.NotificationsOff,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                    Text(
                        text = "Notifications are disabled",
                        color = MaterialTheme.colorScheme.error
                    )
                }
                Text(
                    text = "Enable notifications in System Settings to receive event reminders and newsletter updates.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Button(
                    onClick = { /* Open System Settings logic */ },
                    modifier = Modifier.testTag(TestTags.Notifications.Settings.openSettings)
                ) {
                    Text("Open Settings")
                }
            }
        }
        else -> {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = null,
                        tint = Brand.green
                    )
                    Text(
                        text = "Stay updated",
                        fontWeight = FontWeight.Bold,
                        color = Brand.ink
                    )
                }
                Text(
                    text = "Allow notifications for event reminders and new newsletters.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                SHPEPrimaryButton(
                    onClick = onEnable,
                    modifier = Modifier.testTag(TestTags.Notifications.Settings.enable)
                ) {
                    Text("Enable Notifications")
                }
            }
        }
    }
}
