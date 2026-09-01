package com.unt.shpe.shared.mock

import com.unt.shpe.features.account.model.AccountNotification
import com.unt.shpe.features.events.model.Event
import com.unt.shpe.features.events.model.SampleEvents
import java.time.LocalDateTime

/**
 * Mock data store for demo/testing purposes.
 * Maps 1:1 with iOS MockDataStore.
 */
object MockDataStore {
    fun getMockEvents(): List<Event> = SampleEvents.samples

    fun getMockNotifications(): List<AccountNotification> = listOf(
        AccountNotification(
            category = AccountNotification.Category.REMINDER,
            title = "Upcoming Event",
            message = "General Body Meeting starts at 6:00 PM",
            date = LocalDateTime.now(),
            isRead = false
        ),
        AccountNotification(
            category = AccountNotification.Category.NEWSLETTER,
            title = "New Newsletter",
            message = "August newsletter has been published",
            date = LocalDateTime.now().minusHours(1),
            isRead = true
        ),
    )
}
