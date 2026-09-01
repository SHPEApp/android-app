package com.unt.shpe.app

/**
 * Centralized test tags and accessibility identifiers for UI automation.
 * Maps 1:1 with iOS AccessibilityID for consistent test coverage.
 */
object TestTags {
    object Authentication {
        const val email = "authentication.email"
        const val password = "authentication.password"
        const val signIn = "authentication.signIn"
        const val error = "authentication.error"
        const val workingDemo = "authentication.demo.working"
        const val failedDemo = "authentication.demo.failed"
    }

    object Tab {
        const val scan = "scan.tab"
        const val events = "events.tab"
        const val newsletters = "newsletters.tab"
        const val account = "account.tab"
    }

    object Scan {
        const val scanner = "scan.scanner"
        const val submit = "scan.submit"
        const val eventSelector = "scan.eventSelector"
        const val success = "scan.success"
        const val successDone = "scan.success.done"
        const val processing = "attendance_processing"
        const val error = "attendance_error"
        const val retry = "attendance_retry"
    }

    object Events {
        const val previousMonth = "events.calendar.previousMonth"
        const val monthTitle = "events.calendar.monthTitle"
        const val nextMonth = "events.calendar.nextMonth"
        const val day = "events.calendar.day"
        const val generalBodyMeeting = "events.event.generalBodyMeeting"
        const val resumeWorkshop = "events.event.resumeWorkshop"
        const val industryPanel = "events.event.industryPanel"
        const val scanAttendance = "events.detail.scanAttendance"
    }

    object Newsletters {
        const val primavera = "newsletters.item.primavera"
        const val abril = "newsletters.item.abril"
        const val marzo = "newsletters.item.marzo"
        const val febrero = "newsletters.item.febrero"
        const val detail = "newsletters.detail"
        const val read = "newsletters.detail.read"
        const val download = "newsletters.detail.download"
    }

    object Account {
        const val attendanceHistory = "account.attendanceHistory"
        const val rsvpdEvents = "account.rsvpdEvents"
        const val savedNewsletters = "account.savedNewsletters"
        const val notifications = "account.notifications"
        const val about = "account.about"
        const val signOut = "account.signOut"
    }

    object Notifications {
        const val loading = "notifications.loading"
        const val empty = "notifications.empty"
        const val error = "notifications.error"

        object Settings {
            const val screen = "notifications.settings"
            const val eventReminders = "notifications.settings.eventReminders"
            const val newsletterUpdates = "notifications.settings.newsletterUpdates"
            const val enable = "notifications.settings.enable"
            const val openSettings = "notifications.settings.openSettings"
            const val permissionStatus = "notifications.settings.permissionStatus"
        }
    }
}
