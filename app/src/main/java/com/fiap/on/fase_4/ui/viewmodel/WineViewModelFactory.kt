package com.fiap.on.fase_4.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fiap.on.fase_4.data.repository.WineRepository

class WineViewModelFactory(private val repository: WineRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WineViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WineViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}