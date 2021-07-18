package com.example.ca_kotlin.dao

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Vehicule::class], version = 1)
abstract class AppDatabase : RoomDatabase()
{
    abstract fun vehiculeDAO(): VehiculeDAO
}