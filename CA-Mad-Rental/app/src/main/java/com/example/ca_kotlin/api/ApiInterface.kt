package com.example.ca_kotlin.api

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("exchange/madrental/get-vehicules.php")
    fun getVehicules(): Call<List<Vehicules>>

}