package com.fiap.on.fase_4.data.repository

import com.fiap.on.fase_4.data.local.WineDao
import com.fiap.on.fase_4.data.model.Wine
import kotlinx.coroutines.flow.Flow

class WineRepository(private val wineDao: WineDao) {
    val allWines: Flow<List<Wine>> = wineDao.getAllWines()

    fun getWineById(id: Int): Flow<Wine?> = wineDao.getWineById(id)

    suspend fun insert(wine: Wine) {
        wineDao.insert(wine)
    }

    suspend fun update(wine: Wine) {
        wineDao.update(wine)
    }

    suspend fun delete(wine: Wine) {
        wineDao.delete(wine)
    }
}
