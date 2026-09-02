package com.unt.shpe.features.authentication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.unt.shpe.app.TestTags
import com.unt.shpe.design.*
import com.unt.shpe.features.authentication.model.DemoCredentials
import com.unt.shpe.features.authentication.viewmodel.SessionViewModel

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

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Brand.background)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            // Header
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "UNT SHPE",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = Brand.green
                )
                Text(
                    text = "Sign in to continue",
                    style = MaterialTheme.typography.titleMedium,
                    color = Brand.ink
                )
                Text(
                    text = "Access attendance, events, newsletters, and your member account.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Credentials Form
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                TextField(
                    value = email,
                    onValueChange = viewModel::updateEmail,
                    placeholder = { Text("Email address") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(TestTags.Authentication.email),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = MaterialTheme.shapes.medium,
                    enabled = !isLoading,
                    singleLine = true
                )

                TextField(
                    value = password,
                    onValueChange = viewModel::updatePassword,
                    placeholder = { Text("Password") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(TestTags.Authentication.password),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = MaterialTheme.shapes.medium,
                    enabled = !isLoading,
                    singleLine = true
                )
            }

            // Demo Credentials
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                SHPESectionTitle("DEMO LOGIN")

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(
                        onClick = {
                            viewModel.updateEmail(DemoCredentials.workingEmail)
                            viewModel.updatePassword(DemoCredentials.workingPassword)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .testTag(TestTags.Authentication.workingDemo),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Brand.green.copy(alpha = 0.1f),
                            contentColor = Brand.green
                        ),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text("Working login")
                    }

                    Button(
                        onClick = {
                            viewModel.updateEmail(DemoCredentials.failedEmail)
                            viewModel.updatePassword(DemoCredentials.failedPassword)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .testTag(TestTags.Authentication.failedDemo),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red.copy(alpha = 0.1f),
                            contentColor = Color.Red
                        ),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text("Failed login")
                    }
                }

                Text(
                    text = "Working: demo@unt.edu / SHPE2026!",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Sign In Button
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                SHPEPrimaryButton(
                    onClick = viewModel::signIn,
                    modifier = Modifier.testTag(TestTags.Authentication.signIn)
                ) {
                    Text("Sign In")
                }

                if (errorMessage != null) {
                    Text(
                        text = errorMessage!!,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Red,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag(TestTags.Authentication.error)
                    )
                }
            }

            Text(
                text = "Authentication service connection will be added once the approved UNT or SHPE provider is confirmed.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        SHPELoadingOverlay(isVisible = state == SessionViewModel.State.SIGNING_IN)

        if (state == SessionViewModel.State.SIGNED_IN) {
            onSignInSuccess()
        }
    }
}
