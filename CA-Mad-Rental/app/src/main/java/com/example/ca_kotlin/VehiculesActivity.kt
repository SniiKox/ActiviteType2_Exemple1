package com.example.ca_kotlin

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ca_kotlin.adapters.VehiculeAdapter
import com.example.ca_kotlin.api.ApiClient
import com.example.ca_kotlin.api.Vehicules
import com.example.ca_kotlin.dao.AppDatabaseHelper
import com.example.ca_kotlin.dao.Vehicule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class VehiculesActivity : AppCompatActivity() {
    private var listeFinale: ArrayList<Vehicules> = ArrayList()

    companion object {
        const val PROGRESS_BAR = "Récupération des véhicules en cours"
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_vehicules)

        val recycler = findViewById<RecyclerView>(R.id.list_vehicule)
        recycler.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this)
        recycler.layoutManager = layoutManager

        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle(VehiculesActivity.PROGRESS_BAR)
        progressDialog.setCancelable(false)
        progressDialog.show()

        val adapter = VehiculeAdapter(listeFinale)
        recycler.adapter = adapter

        val favoris = findViewById<Switch>(R.id.favoris)

        favoris.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            if (b)
            {
                displayFavoritesVehicules(adapter, recycler, progressDialog)
                Toast.makeText(
                    this@VehiculesActivity,
                    "Switch On",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                displayAllVehicules(adapter, recycler, progressDialog)
                Toast.makeText(
                    this@VehiculesActivity,
                    "Switch Off",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        displayAllVehicules(adapter, recycler, progressDialog)
    }

    fun displayFavoritesVehicules(adapter: VehiculeAdapter, recycler: RecyclerView, progressDialog: ProgressDialog) {
        val vehicules = AppDatabaseHelper.getDatabase(this)
            .vehiculeDAO()
            .getVehicules()

        val listeFiltree = listeFinale.filter {
                dVehicle -> (vehicules.find {it.vehiculeId == dVehicle.vehiculeId}) != null
        }
        listeFinale.clear()
        listeFinale.addAll(listeFiltree)
        adapter.update(listeFinale.clone() as ArrayList<Vehicules>)
        adapter.notifyDataSetChanged()
    }

    fun displayAllVehicules(adapter: VehiculeAdapter, recycler: RecyclerView, progressDialog: ProgressDialog) {
        ApiClient.getClient.getVehicules().enqueue(object : Callback<List<Vehicules>> {
            override fun onResponse(call: Call<List<Vehicules>>?, response: Response<List<Vehicules>>?) {

                listeFinale.clear()
                listeFinale.addAll(response!!.body()!!)
                adapter.update(listeFinale.clone() as ArrayList<Vehicules>)
                adapter.notifyDataSetChanged()
                progressDialog.dismiss()
            }

            override fun onFailure(call: Call<List<Vehicules>>?, t: Throwable?) {
                progressDialog.dismiss()
            }
        })
    }

    override fun onBackPressed() {
        this.finishAffinity()
    }

}
