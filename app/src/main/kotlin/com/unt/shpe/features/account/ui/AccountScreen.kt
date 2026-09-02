package com.unt.shpe.features.account.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.unt.shpe.app.TestTags
import com.unt.shpe.design.*
import com.unt.shpe.features.account.service.NotificationPermissionService
import com.unt.shpe.features.account.viewmodel.AccountViewModel
import com.unt.shpe.features.account.viewmodel.NotificationSettingsViewModel
import com.unt.shpe.features.account.viewmodel.NotificationsViewModel
import com.unt.shpe.features.authentication.viewmodel.SessionViewModel

enum class AccountSubScreen {
    MAIN,
    ATTENDANCE_HISTORY,
    RSVPD_EVENTS,
    SAVED_NEWSLETTERS,
    NOTIFICATIONS,
    NOTIFICATION_SETTINGS,
    ABOUT
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    accountViewModel: AccountViewModel,
    sessionViewModel: SessionViewModel,
    onSignOut: () -> Unit,
) {
    var currentSubScreen by remember { mutableStateOf(AccountSubScreen.MAIN) }
    
    when (currentSubScreen) {
        AccountSubScreen.MAIN -> {
            AccountMainContent(
                accountViewModel = accountViewModel,
                sessionViewModel = sessionViewModel,
                onSignOut = onSignOut,
                onNavigate = { currentSubScreen = it }
            )
        }
        AccountSubScreen.ATTENDANCE_HISTORY -> {
            SubScreenWrapper(title = "My Attendance History", onBack = { currentSubScreen = AccountSubScreen.MAIN }) {
                AttendanceHistoryScreen()
            }
        }
        AccountSubScreen.RSVPD_EVENTS -> {
            SubScreenWrapper(title = "My RSVP'd Events", onBack = { currentSubScreen = AccountSubScreen.MAIN }) {
                RSVPdEventsScreen()
            }
        }
        AccountSubScreen.SAVED_NEWSLETTERS -> {
            SubScreenWrapper(title = "Saved Newsletters", onBack = { currentSubScreen = AccountSubScreen.MAIN }) {
                SavedNewslettersScreen()
            }
        }
        AccountSubScreen.NOTIFICATIONS -> {
            val notificationsViewModel: NotificationsViewModel = viewModel()
            SubScreenWrapper(
                title = "Notifications",
                onBack = { currentSubScreen = AccountSubScreen.MAIN },
                actions = {
                    IconButton(onClick = { currentSubScreen = AccountSubScreen.NOTIFICATION_SETTINGS }) {
                        Icon(Icons.Default.Settings, contentDescription = "Notification Settings", tint = Color.White)
                    }
                }
            ) {
                NotificationsScreen(viewModel = notificationsViewModel)
            }
        }
        AccountSubScreen.NOTIFICATION_SETTINGS -> {
            val context = LocalContext.current
            val settingsViewModel: NotificationSettingsViewModel = viewModel(
                factory = NotificationSettingsViewModelFactory(NotificationPermissionService(context))
            )
            SubScreenWrapper(title = "Notification Settings", onBack = { currentSubScreen = AccountSubScreen.NOTIFICATIONS }) {
                NotificationSettingsScreen(viewModel = settingsViewModel)
            }
        }
        AccountSubScreen.ABOUT -> {
            SubScreenWrapper(title = "About SHPE", onBack = { currentSubScreen = AccountSubScreen.MAIN }) {
                AboutSHPEScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubScreenWrapper(
    title: String,
    onBack: () -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(title, color = Color.White) },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
            },
            actions = actions,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Brand.green
            )
        )
        content()
    }
}

@Composable
fun AccountMainContent(
    accountViewModel: AccountViewModel,
    sessionViewModel: SessionViewModel,
    onSignOut: () -> Unit,
    onNavigate: (AccountSubScreen) -> Unit
) {
    val memberName by accountViewModel.memberName.collectAsState()
    val memberEmail by accountViewModel.memberEmail.collectAsState()
    val eventsAttended by accountViewModel.eventsAttended.collectAsState()
    val attendanceRate by accountViewModel.attendanceRate.collectAsState()
    val semester by accountViewModel.semester.collectAsState()
    val sessionState by sessionViewModel.state.collectAsState()
    val isLoading = sessionState == SessionViewModel.State.SIGNING_OUT

    Box(modifier = Modifier.fillMaxSize().background(Brand.background)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(vertical = 22.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            // Profile Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = Brand.green
                )

                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = memberName,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Brand.ink
                    )
                    Text(
                        text = "Member",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = memberEmail,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Attendance Summary
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SHPESectionTitle("ATTENDANCE SUMMARY")

                SHPECard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Stat(
                            value = eventsAttended,
                            label = "Events Attended",
                            modifier = Modifier.weight(1f)
                        )
                        VerticalDivider(
                            modifier = Modifier.height(40.dp).padding(horizontal = 8.dp),
                            color = MaterialTheme.colorScheme.outlineVariant
                        )
                        Stat(
                            value = attendanceRate,
                            label = "Attendance Rate",
                            modifier = Modifier.weight(1f)
                        )
                        VerticalDivider(
                            modifier = Modifier.height(40.dp).padding(horizontal = 8.dp),
                            color = MaterialTheme.colorScheme.outlineVariant
                        )
                        Stat(
                            value = semester,
                            label = "Semester",
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Menu Items
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                SHPETableView(items = listOf(Unit)) {
                    Column {
                        SHPEListRow(
                            icon = Icons.Default.CalendarToday,
                            title = "My Attendance History",
                            accessibilityID = TestTags.Account.attendanceHistory,
                            action = { onNavigate(AccountSubScreen.ATTENDANCE_HISTORY) }
                        )
                        SHPEListRow(
                            icon = Icons.Default.EventAvailable,
                            title = "My RSVP'd Events",
                            accessibilityID = TestTags.Account.rsvpdEvents,
                            action = { onNavigate(AccountSubScreen.RSVPD_EVENTS) }
                        )
                        SHPEListRow(
                            icon = Icons.Default.Description,
                            title = "Saved Newsletters",
                            accessibilityID = TestTags.Account.savedNewsletters,
                            action = { onNavigate(AccountSubScreen.SAVED_NEWSLETTERS) }
                        )
                        SHPEListRow(
                            icon = Icons.Default.Notifications,
                            title = "Notifications",
                            accessibilityID = TestTags.Account.notifications,
                            action = { onNavigate(AccountSubScreen.NOTIFICATIONS) }
                        )
                        SHPEListRow(
                            icon = Icons.Default.Info,
                            title = "About SHPE",
                            accessibilityID = TestTags.Account.about,
                            action = { onNavigate(AccountSubScreen.ABOUT) }
                        )
                        SHPEListRow(
                            icon = Icons.AutoMirrored.Filled.ExitToApp,
                            title = "Sign Out",
                            accessibilityID = TestTags.Account.signOut,
                            isDestructive = true,
                            action = {
                                sessionViewModel.signOut()
                                onSignOut()
                            }
                        )
                    }
                }
            }
        }

        SHPELoadingOverlay(isVisible = isLoading)
    }
}

@Composable
private fun Stat(
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Brand.green
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

class NotificationSettingsViewModelFactory(private val service: NotificationPermissionService) : androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        return NotificationSettingsViewModel(service) as T
    }
}
