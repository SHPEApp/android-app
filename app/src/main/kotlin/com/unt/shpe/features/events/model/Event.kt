package com.unt.shpe.features.events.model

import com.unt.shpe.app.TestTags
import java.time.LocalDate
import java.util.UUID

data class Event(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val date: LocalDate,
    val time: String,
    val location: String,
    val description: String,
    val accessibilityID: String,
)

object SampleEvents {
    val samples = listOf(
        Event(
            title = "General Body Meeting",
            date = LocalDate.now(),
            time = "6:00 PM",
            location = "BLB 070",
            description = "Connect with fellow engineers, hear what is ahead for SHPE, and find your place in the community.",
            accessibilityID = TestTags.Events.generalBodyMeeting
        ),
        Event(
            title = "Resume Workshop",
            date = LocalDate.now(),
            time = "6:00 PM",
            location = "BLB 055",
            description = "Bring your resume for practical feedback from SHPE mentors and industry partners.",
            accessibilityID = TestTags.Events.resumeWorkshop
        ),
        Event(
            title = "Industry Panel",
            date = LocalDate.now(),
            time = "6:00 PM",
            location = "BLB 070",
            description = "Learn from engineers building the future and ask questions about life after UNT.",
            accessibilityID = TestTags.Events.industryPanel
        ),
    )
}
