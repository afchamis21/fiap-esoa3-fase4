package com.fiap.on.fase_4.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.fiap.on.fase_4.data.model.Wine
import kotlinx.coroutines.flow.Flow

@Dao
interface WineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wine: Wine)

    @Update
    suspend fun update(wine: Wine)

    @Delete
    suspend fun delete(wine: Wine)

    @Query("SELECT * FROM wines ORDER BY name ASC")
    fun getAllWines(): Flow<List<Wine>>

    @Query("SELECT * FROM wines WHERE id = :id")
    fun getWineById(id: Int): Flow<Wine?>
}
