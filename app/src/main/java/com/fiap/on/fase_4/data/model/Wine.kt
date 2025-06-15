package com.fiap.on.fase_4.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wines")
data class Wine(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val price: Double,
    val description: String
)
