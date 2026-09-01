package com.unt.shpe.features.authentication.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.unt.shpe.app.TestTags
import com.unt.shpe.features.authentication.viewmodel.SessionViewModel

/**
 * Sign-in screen for user authentication.
 * Maps 1:1 with iOS SignInView.
 */
@Composable
fun SignInScreen(
    viewModel: SessionViewModel,
    onSignInSuccess: () -> Unit,
) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val state by viewModel.state.collectAsState()
    val isLoading = state == SessionViewModel.State.SIGNING_IN || state == SessionViewModel.State.SIGNING_OUT

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("SHPE Sign In")

        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            value = email,
            onValueChange = viewModel::updateEmail,
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TestTags.Authentication.email),
            enabled = !isLoading,
        )

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = password,
            onValueChange = viewModel::updatePassword,
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TestTags.Authentication.password),
            enabled = !isLoading,
        )

        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                errorMessage ?: "",
                modifier = Modifier.testTag(TestTags.Authentication.error),
                color = androidx.compose.material3.MaterialTheme.colorScheme.error,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = viewModel::signIn,
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TestTags.Authentication.signIn),
            enabled = !isLoading,
        ) {
            Text(if (isLoading) "Signing In..." else "Sign In")
        }

        if (state == SessionViewModel.State.SIGNED_IN) {
            onSignInSuccess()
        }
    }
}
