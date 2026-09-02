package com.unt.shpe.features.newsletters.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NewslettersViewModel : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _newsletters = MutableStateFlow(emptyList<String>())
    val newsletters: StateFlow<List<String>> = _newsletters.asStateFlow()

    fun loadNewsletters() {
        _isLoading.value = true
        // Load newsletters from backend
        _isLoading.value = false
    }
}
