package com.example.ca_kotlin.adapters

import android.content.Intent
import com.example.ca_kotlin.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ca_kotlin.VehiculeDetailsActivity
import com.example.ca_kotlin.api.Vehicules
import com.squareup.picasso.Picasso

class VehiculeAdapter(list: List<Vehicules>) : RecyclerView.Adapter<VehiculeAdapter.VehiculeViewHolder>() {

    private var list: List<Vehicules> = ArrayList()

    init {
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehiculeViewHolder {
        val viewMemo: View =
            LayoutInflater.from(parent.context).inflate(R.layout.vehicule_selector, parent, false)
        return VehiculeViewHolder(viewMemo)
    }

    override fun onBindViewHolder(holder: VehiculeViewHolder, position: Int) {
        holder.name?.text = list[position].name
        holder.price?.text = "Prix d'achat : " + list[position].price
        holder.category?.text = "Catégorie : " + list[position].category
        Picasso.get()
            .load("http://s519716619.onlinehome.fr/exchange/madrental/images/" + list[position].image)
            .into(holder.image!!)
    }

    fun update(list: ArrayList<Vehicules>) {
        this.list = list
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class VehiculeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView? = null
        var price: TextView? = null
        var category: TextView? = null
        var image: ImageView? = null

        init {
            name = itemView.findViewById(R.id.name)
            price = itemView.findViewById(R.id.price)
            category = itemView.findViewById(R.id.category)
            image = itemView.findViewById(R.id.image)

            itemView.setOnClickListener {
                val context = itemView.context

                println(list[adapterPosition])
                context.startActivity(Intent(context, VehiculeDetailsActivity::class.java)
                    .putExtra("name", list[adapterPosition].name)
                    .putExtra("price", list[adapterPosition].price)
                    .putExtra("category", list[adapterPosition].category)
                    .putExtra("image", list[adapterPosition].image)
                    .putExtra("id", list[adapterPosition].vehiculeId.toString())
                )
            }
        }
    }

}