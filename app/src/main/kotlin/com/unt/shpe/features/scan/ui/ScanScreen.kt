package com.unt.shpe.features.scan.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.unt.shpe.app.TestTags
import com.unt.shpe.features.events.model.SampleEvents
import com.unt.shpe.features.scan.viewmodel.ScanViewModel

@Composable
fun ScanScreen(viewModel: ScanViewModel) {
    val selectedEvent by viewModel.selectedEvent.collectAsState()
    val isProcessing by viewModel.isProcessing.collectAsState()
    val isShowingSuccess by viewModel.isShowingSuccess.collectAsState()
    var expandedDropdown by remember { mutableStateOf(false) }

    if (isShowingSuccess) {
        ScanSuccessOverlay(
            onDismiss = viewModel::resetSuccess,
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Scan Attendance")

        Spacer(modifier = Modifier.height(24.dp))

        // Camera scanner placeholder
        if (!isProcessing) {
            Button(
                onClick = {},
                modifier = Modifier.testTag(TestTags.Scan.scanner),
            ) {
                Text("Open Camera")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Event selector dropdown
        Button(
            onClick = { expandedDropdown = !expandedDropdown },
            modifier = Modifier.testTag(TestTags.Scan.eventSelector),
        ) {
            Text(selectedEvent.title)
        }

        DropdownMenu(
            expanded = expandedDropdown,
            onDismissRequest = { expandedDropdown = false },
        ) {
            SampleEvents.samples.forEach { event ->
                DropdownMenuItem(
                    text = { Text(event.title) },
                    onClick = {
                        viewModel.selectEvent(event)
                        expandedDropdown = false
                    },
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (isProcessing) {
            CircularProgressIndicator(modifier = Modifier.testTag(TestTags.Scan.processing))
            Text("Processing...")
        } else {
            Button(
                onClick = viewModel::submitDemoScan,
                modifier = Modifier.testTag(TestTags.Scan.submit),
            ) {
                Text("Submit Scan")
            }
        }
    }
}

@Composable
fun ScanSuccessOverlay(onDismiss: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            "Attendance Recorded!",
            modifier = Modifier.testTag(TestTags.Scan.success),
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onDismiss,
            modifier = Modifier.testTag(TestTags.Scan.successDone),
        ) {
            Text("Done")
        }
    }
}
