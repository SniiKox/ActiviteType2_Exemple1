package com.example.ca_kotlin.dao

import androidx.room.*

@Dao
abstract class VehiculeDAO {

    @Query("SELECT * FROM vehicules")
    abstract fun getVehicules(): List<Vehicule>

    @Query("SELECT * FROM vehicules WHERE vehiculeId = :id LIMIT 1")
    abstract fun find(id: Long): Vehicule

    @Query("DELETE FROM vehicules WHERE vehiculeId = :id")
    abstract fun delete(id: Long)

    @Insert
    abstract fun insert(vararg vehicule: Vehicule)

    @Update
    abstract fun update(vararg vehicule: Vehicule)




}