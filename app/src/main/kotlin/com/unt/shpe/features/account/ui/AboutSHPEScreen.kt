package com.unt.shpe.features.account.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.unt.shpe.design.Brand

@Composable
fun AboutSHPEScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "About SHPE",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Brand.ink
        )

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                text = "Society of Hispanic Professional Engineers",
                style = MaterialTheme.typography.titleMedium,
                color = Brand.ink
            )

            Text(
                text = "Empowering Hispanic students in STEM through community, leadership, and opportunity.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                text = "Contact",
                style = MaterialTheme.typography.titleMedium,
                color = Brand.ink
            )

            Text(
                text = "Email: shpe@unt.edu",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
