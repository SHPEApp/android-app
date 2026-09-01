package com.unt.shpe.features.account.model

import java.time.LocalDateTime
import java.util.UUID

data class AccountNotification(
    val id: String = UUID.randomUUID().toString(),
    val category: Category,
    val title: String,
    val message: String,
    val date: LocalDateTime,
    val isRead: Boolean = false,
) {
    enum class Category(val displayName: String) {
        REMINDER("Reminder"),
        NEWSLETTER("Newsletter"),
    }

    val accessibilityID: String
        get() = "account.notifications.$id"
}
