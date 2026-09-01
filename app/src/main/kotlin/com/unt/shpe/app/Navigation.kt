package com.unt.shpe.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Calendar
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.unt.shpe.design.ShpeTheme
import com.unt.shpe.features.account.ui.AccountScreen
import com.unt.shpe.features.account.viewmodel.AccountViewModel
import com.unt.shpe.features.authentication.ui.SignInScreen
import com.unt.shpe.features.authentication.viewmodel.SessionViewModel
import com.unt.shpe.features.events.ui.EventsScreen
import com.unt.shpe.features.events.viewmodel.EventsViewModel
import com.unt.shpe.features.newsletters.ui.NewslettersScreen
import com.unt.shpe.features.newsletters.viewmodel.NewslettersViewModel
import com.unt.shpe.features.scan.ui.ScanScreen
import com.unt.shpe.features.scan.viewmodel.ScanViewModel

/**
 * Root app navigation and main content view.
 * Maps 1:1 with iOS ContentView and AppRootView.
 */
@Composable
fun ShipApp() {
    ShpeTheme {
        var isSignedIn by remember { mutableStateOf(false) }
        val sessionViewModel: SessionViewModel = viewModel()

        if (!isSignedIn) {
            SignInScreen(
                viewModel = sessionViewModel,
                onSignInSuccess = { isSignedIn = true },
            )
        } else {
            ShipMainContent(
                sessionViewModel = sessionViewModel,
                onSignOut = { isSignedIn = false },
            )
        }
    }
}

@Composable
fun ShipMainContent(
    sessionViewModel: SessionViewModel,
    onSignOut: () -> Unit,
) {
    var selectedTab by remember { mutableStateOf(AppTab.SCAN) }

    val scanViewModel: ScanViewModel = viewModel()
    val eventsViewModel: EventsViewModel = viewModel()
    val newslettersViewModel: NewslettersViewModel = viewModel()
    val accountViewModel: AccountViewModel = viewModel()

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Text("QR") },
                    label = { Text("Scan") },
                    selected = selectedTab == AppTab.SCAN,
                    onClick = { selectedTab = AppTab.SCAN },
                    modifier = Modifier.testTag(TestTags.Tab.scan),
                )
                NavigationBarItem(
                    icon = { Text("📅") },
                    label = { Text("Events") },
                    selected = selectedTab == AppTab.EVENTS,
                    onClick = { selectedTab = AppTab.EVENTS },
                    modifier = Modifier.testTag(TestTags.Tab.events),
                )
                NavigationBarItem(
                    icon = { Text("📰") },
                    label = { Text("Newsletters") },
                    selected = selectedTab == AppTab.NEWSLETTERS,
                    onClick = { selectedTab = AppTab.NEWSLETTERS },
                    modifier = Modifier.testTag(TestTags.Tab.newsletters),
                )
                NavigationBarItem(
                    icon = { Text("👤") },
                    label = { Text("Account") },
                    selected = selectedTab == AppTab.ACCOUNT,
                    onClick = { selectedTab = AppTab.ACCOUNT },
                    modifier = Modifier.testTag(TestTags.Tab.account),
                )
            }
        },
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            when (selectedTab) {
                AppTab.SCAN -> ScanScreen(scanViewModel)
                AppTab.EVENTS -> EventsScreen(eventsViewModel)
                AppTab.NEWSLETTERS -> NewslettersScreen(newslettersViewModel)
                AppTab.ACCOUNT -> AccountScreen(
                    accountViewModel = accountViewModel,
                    sessionViewModel = sessionViewModel,
                    onSignOut = onSignOut,
                )
            }
        }
    }
}
