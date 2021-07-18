package com.example.ca_kotlin.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicules")
class Vehicule(
    @PrimaryKey(autoGenerate = true)
    val vehiculeId: Long = 0,
    val name: String? = null,
    val price: String? = null,
    val category: String? = null,
    val image: String? = null
)