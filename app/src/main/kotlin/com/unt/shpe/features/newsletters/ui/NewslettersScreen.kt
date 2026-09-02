package com.unt.shpe.features.newsletters.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.unt.shpe.app.TestTags
import com.unt.shpe.design.*
import com.unt.shpe.features.newsletters.model.Newsletter
import com.unt.shpe.features.newsletters.viewmodel.NewslettersViewModel

@Composable
fun NewslettersScreen(viewModel: NewslettersViewModel) {
    val selectedNewsletter by viewModel.selectedNewsletter.collectAsState()

    if (selectedNewsletter != null) {
        NewsletterDetailScreen(
            newsletter = selectedNewsletter!!,
            onBack = viewModel::clearSelection
        )
    } else {
        NewslettersMainScreen(viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewslettersMainScreen(viewModel: NewslettersViewModel) {
    val newsletters by viewModel.newsletters.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Newsletters", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Brand.green)
            )
        },
        containerColor = Brand.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SHPESectionTitle(title = "LATEST NEWSLETTERS")

            if (isLoading) {
                Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Brand.green)
                }
            } else {
                newsletters.forEach { newsletter ->
                    NewsletterCard(
                        newsletter = newsletter,
                        onClick = { viewModel.selectNewsletter(newsletter) }
                    )
                }
            }
        }
    }
}

@Composable
fun NewsletterCard(
    newsletter: Newsletter,
    onClick: () -> Unit
) {
    SHPECard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag(newsletter.accessibilityID)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(width = 64.dp, height = 78.dp)
                    .background(Brand.green.copy(alpha = 0.1f), RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (newsletter.icon == "leaf") Icons.Default.Eco else Icons.Default.Description,
                    contentDescription = null,
                    tint = Brand.green,
                    modifier = Modifier.size(32.dp)
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = newsletter.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Brand.ink
                )
                Text(
                    text = "Newsletter",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = newsletter.date,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Icon(
                imageVector = Icons.Default.ArrowCircleDown,
                contentDescription = null,
                tint = Brand.green
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsletterDetailScreen(
    newsletter: Newsletter,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Newsletter", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Brand.green)
            )
        },
        modifier = Modifier.testTag(TestTags.Newsletters.detail)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Newspaper,
                contentDescription = null,
                modifier = Modifier.size(74.dp),
                tint = Brand.green
            )
            
            Text(
                text = newsletter.title,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Brand.ink,
                textAlign = TextAlign.Center
            )
            
            Text(
                text = "SHPE Newsletter",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SHPEPrimaryButton(
                    onClick = {},
                    modifier = Modifier.weight(1f).testTag(TestTags.Newsletters.read)
                ) {
                    Icon(Icons.Default.Book, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Read")
                }

                Button(
                    onClick = {},
                    modifier = Modifier.testTag(TestTags.Newsletters.download),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Brand.green
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Brand.green),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(Icons.Default.ArrowDownward, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Download")
                }
            }
        }
    }
}
