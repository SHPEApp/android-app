package com.unt.shpe.features.newsletters.viewmodel

import androidx.lifecycle.ViewModel
import com.unt.shpe.features.newsletters.model.Newsletter
import com.unt.shpe.features.newsletters.model.SampleNewsletters
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NewslettersViewModel : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _newsletters = MutableStateFlow(SampleNewsletters.samples)
    val newsletters: StateFlow<List<Newsletter>> = _newsletters.asStateFlow()

    private val _selectedNewsletter = MutableStateFlow<Newsletter?>(null)
    val selectedNewsletter: StateFlow<Newsletter?> = _selectedNewsletter.asStateFlow()

    fun selectNewsletter(newsletter: Newsletter) {
        _selectedNewsletter.value = newsletter
    }

    fun clearSelection() {
        _selectedNewsletter.value = null
    }

    fun loadNewsletters() {
        _isLoading.value = true
        // Load newsletters from backend
        _isLoading.value = false
    }
}
