package com.unt.shpe.features.events.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.unt.shpe.features.events.model.Event
import com.unt.shpe.features.events.viewmodel.EventsViewModel

/**
 * Events/calendar screen displaying upcoming events.
 * Maps 1:1 with iOS EventsView.
 */
@Composable
fun EventsScreen(viewModel: EventsViewModel) {
    val events by viewModel.events.collectAsState()
    val selectedEvent by viewModel.selectedEvent.collectAsState()

    if (selectedEvent != null) {
        EventDetailScreen(
            event = selectedEvent!!,
            onBack = viewModel::clearSelection,
        )
    } else {
        EventsListScreen(
            events = events,
            onEventSelected = viewModel::selectEvent,
        )
    }
}

@Composable
fun EventsListScreen(
    events: List<Event>,
    onEventSelected: (Event) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Text("Events & Calendar")

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(events) { event ->
                EventCard(
                    event = event,
                    onClick = { onEventSelected(event) },
                )
            }
        }
    }
}

@Composable
fun EventCard(
    event: Event,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(event.accessibilityID),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(event.title)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Date: ${event.date}")
            Text("Time: ${event.time}")
            Text("Location: ${event.location}")
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = onClick) {
                Text("View Details")
            }
        }
    }
}

@Composable
fun EventDetailScreen(
    event: Event,
    onBack: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Button(onClick = onBack) {
            Text("← Back")
        }

        Text(event.title)
        Text("Date: ${event.date}")
        Text("Time: ${event.time}")
        Text("Location: ${event.location}")
        Text(event.description)

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TestTags.Events.scanAttendance),
        ) {
            Text("Scan Attendance")
        }
    }
}

object TestTags {
    object Events {
        const val scanAttendance = "events.detail.scanAttendance"
    }
}
