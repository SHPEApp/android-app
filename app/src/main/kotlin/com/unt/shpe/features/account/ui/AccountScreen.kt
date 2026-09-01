package com.unt.shpe.features.account.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.unt.shpe.app.TestTags
import com.unt.shpe.features.account.viewmodel.AccountViewModel
import com.unt.shpe.features.authentication.viewmodel.SessionViewModel

/**
 * Account/member screen displaying profile and options.
 * Maps 1:1 with iOS AccountView.
 */
@Composable
fun AccountScreen(
    accountViewModel: AccountViewModel,
    sessionViewModel: SessionViewModel,
    onSignOut: () -> Unit,
) {
    val memberName by accountViewModel.memberName.collectAsState()
    val attendanceCount by accountViewModel.attendanceCount.collectAsState()
    val isLoading by sessionViewModel.isLoading.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text("Account & Settings")

        Spacer(modifier = Modifier.height(16.dp))

        Text("Member: $memberName")
        Text("Attendance Count: $attendanceCount")

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TestTags.Account.attendanceHistory),
        ) {
            Text("Attendance History")
        }

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TestTags.Account.rsvpdEvents),
        ) {
            Text("RSVP'd Events")
        }

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TestTags.Account.savedNewsletters),
        ) {
            Text("Saved Newsletters")
        }

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TestTags.Account.notifications),
        ) {
            Text("Notifications")
        }

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TestTags.Account.about),
        ) {
            Text("About SHPE")
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                sessionViewModel.signOut()
                onSignOut()
            },
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TestTags.Account.signOut),
            enabled = !isLoading,
        ) {
            Text(if (isLoading) "Signing Out..." else "Sign Out")
        }
    }
}
