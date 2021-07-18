package com.example.ca_kotlin

import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.ca_kotlin.dao.AppDatabaseHelper
import com.example.ca_kotlin.dao.Vehicule

class VehiculeDetailsActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_vehicule_details)

        val name = intent.getStringExtra("name")
        val price = intent.getStringExtra("price")
        val category = intent.getStringExtra("category")
        val image = intent.getStringExtra("image")
        val id = intent.getStringExtra("id")

        val textView = findViewById<TextView>(R.id.text)
        textView.text = "Nom du véhicule : " + name + "\nPrix d'achat : " + price + "\nCatégorie : " + category

        val button = findViewById<Button>(R.id.favorites)

        button.setOnClickListener {
            val objectDao = AppDatabaseHelper.getDatabase(this)
                .vehiculeDAO()
            val currentVehicule = objectDao
                .find(id.toLong())

            try {
                objectDao.insert(Vehicule(
                    id.toLong(),
                    name, price, category, image
                ))
            } catch (e: SQLiteConstraintException) {
                return@setOnClickListener
            }

            var intent = Intent(this, VehiculesActivity::class.java)
            startActivity(intent)

        }
    }

}