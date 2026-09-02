package com.unt.shpe.features.newsletters.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.unt.shpe.features.newsletters.viewmodel.NewslettersViewModel

@Composable
fun NewslettersScreen(viewModel: NewslettersViewModel) {
    val newsletters by viewModel.newsletters.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Text("Newsletters")

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            Text("Loading newsletters...")
        } else if (newsletters.isEmpty()) {
            Text("No newsletters available")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(newsletters.size) { index ->
                    Text(newsletters[index])
                }
            }
        }
    }
}
