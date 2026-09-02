package com.unt.shpe.features.account.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AccountViewModel : ViewModel() {
    private val _memberName = MutableStateFlow("SHPE Member")
    val memberName: StateFlow<String> = _memberName.asStateFlow()

    private val _memberEmail = MutableStateFlow("member@unt.edu")
    val memberEmail: StateFlow<String> = _memberEmail.asStateFlow()

    private val _eventsAttended = MutableStateFlow("12")
    val eventsAttended: StateFlow<String> = _eventsAttended.asStateFlow()

    private val _attendanceRate = MutableStateFlow("96%")
    val attendanceRate: StateFlow<String> = _attendanceRate.asStateFlow()

    private val _semester = MutableStateFlow("Spring")
    val semester: StateFlow<String> = _semester.asStateFlow()

    private val _attendanceCount = MutableStateFlow(12)
    val attendanceCount: StateFlow<Int> = _attendanceCount.asStateFlow()

    fun loadMemberInfo() {
        // Load from backend
        _memberName.value = "Demo Member"
        _attendanceCount.value = 5
    }
}
