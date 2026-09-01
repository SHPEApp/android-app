package com.unt.shpe.features.account.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel for account feature.
 * Manages member information, attendance history, and settings.
 * Maps 1:1 with iOS AccountViewModel.
 */
class AccountViewModel : ViewModel() {
    private val _memberName = MutableStateFlow("")
    val memberName: StateFlow<String> = _memberName.asStateFlow()

    private val _attendanceCount = MutableStateFlow(0)
    val attendanceCount: StateFlow<Int> = _attendanceCount.asStateFlow()

    fun loadMemberInfo() {
        // Load from backend
        _memberName.value = "Demo Member"
        _attendanceCount.value = 5
    }
}
