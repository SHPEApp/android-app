package com.unt.shpe.features.scan.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.unt.shpe.app.TestTags
import com.unt.shpe.design.*
import com.unt.shpe.features.events.model.SampleEvents
import com.unt.shpe.features.scan.viewmodel.ScanViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanScreen(viewModel: ScanViewModel) {
    val selectedEvent by viewModel.selectedEvent.collectAsState()
    val isProcessing by viewModel.isProcessing.collectAsState()
    val isShowingSuccess by viewModel.isShowingSuccess.collectAsState()
    var expandedDropdown by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Scan Attendance", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Brand.green)
            )
        },
        containerColor = Brand.background
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(22.dp)
            ) {
                // Scanner Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(18.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = Brand.green,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "Ready to check in",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = Brand.green
                            )
                        }

                        Box(
                            modifier = Modifier
                                .height(245.dp)
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                                .clip(RoundedCornerShape(18.dp))
                                .background(Color.White)
                                .testTag(TestTags.Scan.scanner),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.QrCode,
                                contentDescription = null,
                                modifier = Modifier.size(92.dp),
                                tint = Brand.ink.copy(alpha = 0.72f)
                            )

                            androidx.compose.foundation.Canvas(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(34.dp)
                            ) {
                                val strokeWidth = 4.dp.toPx()
                                val cornerLength = 28.dp.toPx()
                                val color = Brand.green

                                // Top Left
                                drawPath(
                                    path = Path().apply {
                                        moveTo(0f, cornerLength)
                                        lineTo(0f, 0f)
                                        lineTo(cornerLength, 0f)
                                    },
                                    color = color,
                                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                                )

                                // Top Right
                                drawPath(
                                    path = Path().apply {
                                        moveTo(size.width - cornerLength, 0f)
                                        lineTo(size.width, 0f)
                                        lineTo(size.width, cornerLength)
                                    },
                                    color = color,
                                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                                )

                                // Bottom Left
                                drawPath(
                                    path = Path().apply {
                                        moveTo(0f, size.height - cornerLength)
                                        lineTo(0f, size.height)
                                        lineTo(cornerLength, size.height)
                                    },
                                    color = color,
                                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                                )

                                // Bottom Right
                                drawPath(
                                    path = Path().apply {
                                        moveTo(size.width - cornerLength, size.height)
                                        lineTo(size.width, size.height)
                                        lineTo(size.width, size.height - cornerLength)
                                    },
                                    color = color,
                                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                                )
                            }
                        }

                        Text(
                            text = if (isProcessing) "Processing attendance..." else "Point the camera at the QR / barcode",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        SHPEPrimaryButton(
                            onClick = viewModel::submitDemoScan,
                            modifier = Modifier
                                .padding(horizontal = 20.dp)
                                .testTag(TestTags.Scan.submit)
                        ) {
                            Icon(imageVector = Icons.Default.QrCodeScanner, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Simulate scan")
                        }
                    }
                }

                // Event Selector Section
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    SHPESectionTitle("CHECK IN TO")

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White)
                            .clickable { expandedDropdown = true }
                            .padding(16.dp)
                            .testTag(TestTags.Scan.eventSelector)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                                Text(
                                    text = "SHPE Event",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = selectedEvent.title,
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Brand.ink
                                )
                            }
                            Icon(
                                imageVector = Icons.Default.UnfoldMore,
                                contentDescription = null,
                                tint = Brand.green
                            )
                        }

                        DropdownMenu(
                            expanded = expandedDropdown,
                            onDismissRequest = { expandedDropdown = false }
                        ) {
                            SampleEvents.samples.forEach { event ->
                                DropdownMenuItem(
                                    text = { Text(event.title) },
                                    onClick = {
                                        viewModel.selectEvent(event)
                                        expandedDropdown = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // Success Overlay
            if (isShowingSuccess) {
                ScanSuccessOverlay(
                    eventTitle = selectedEvent.title,
                    onDismiss = viewModel::resetSuccess
                )
            }
        }
    }
}

@Composable
fun ScanSuccessOverlay(
    eventTitle: String,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.4f))
            .clickable(enabled = false) {}
            .testTag(TestTags.Scan.success),
        contentAlignment = Alignment.BottomCenter
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    modifier = Modifier.size(72.dp),
                    tint = Brand.green
                )
                Text(
                    text = "Attendance Recorded",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Brand.ink
                )
                Text(
                    text = eventTitle,
                    style = MaterialTheme.typography.titleMedium,
                    color = Brand.ink
                )
                Text(
                    text = "You're checked in",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .testTag(TestTags.Scan.successDone),
                    colors = ButtonDefaults.buttonColors(containerColor = Brand.green),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text("Done", color = Color.White)
                }
            }
        }
    }
}
