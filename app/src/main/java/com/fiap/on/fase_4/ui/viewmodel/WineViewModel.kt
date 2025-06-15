package com.fiap.on.fase_4.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiap.on.fase_4.data.model.Wine
import com.fiap.on.fase_4.data.repository.WineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class WineViewModel(private val repository: WineRepository) : ViewModel() {

    val allWines: Flow<List<Wine>> = repository.allWines

    fun getWineById(id: Int): Flow<Wine?> = repository.getWineById(id)

    fun insert(wine: Wine) = viewModelScope.launch {
        repository.insert(wine)
    }

    fun update(wine: Wine) = viewModelScope.launch {
        repository.update(wine)
    }

    fun delete(wine: Wine) = viewModelScope.launch {
        repository.delete(wine)
    }
}