package com.unt.shpe.features.newsletters.model

import com.unt.shpe.app.TestTags

data class Newsletter(
    val id: String,
    val title: String,
    val date: String,
    val icon: String,
    val accessibilityID: String
)

object SampleNewsletters {
    val samples = listOf(
        Newsletter(id = "primavera-2026", title = "Primavera 2026", date = "May 2026", icon = "leaf", accessibilityID = TestTags.Newsletters.primavera),
        Newsletter(id = "abril-2026", title = "Abril 2026", date = "Apr 2026", icon = "description", accessibilityID = TestTags.Newsletters.abril),
        Newsletter(id = "marzo-2026", title = "Marzo 2026", date = "Mar 2026", icon = "description", accessibilityID = TestTags.Newsletters.marzo),
        Newsletter(id = "febrero-2026", title = "Febrero 2026", date = "Feb 2026", icon = "description", accessibilityID = TestTags.Newsletters.febrero)
    )
}
