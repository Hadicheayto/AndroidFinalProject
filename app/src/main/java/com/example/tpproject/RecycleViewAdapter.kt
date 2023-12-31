package com.example.tpproject

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.tpproject.data.Property
import com.bumptech.glide.Glide

class RecycleViewAdapter(
    private val propertyList:List<Property>,
    private val isDeletable: Boolean = false,
    private val clickListener:(Property) -> Unit
) : RecyclerView.Adapter<MyViewHolder>(){

    private var filteredList: List<Property> = propertyList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.list_item,parent,false)
        return MyViewHolder(listItem,isDeletable)
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.Bind(filteredList[position], clickListener)
    }

    fun filterList(query: String) {
        filteredList = propertyList.filter { property ->
            property.title.contains(query, ignoreCase = true)
        }
        notifyDataSetChanged()
    }



}

class MyViewHolder(val view:View, private val isDeletable: Boolean): RecyclerView.ViewHolder(view){


    val title = view.findViewById<TextView>(R.id.tvTitle)
    val location  = view.findViewById<TextView>(R.id.tvLocation)
    val price  = view.findViewById<TextView>(R.id.tvPrice)
    val img = view.findViewById<ImageView>(R.id.ivImageProperty)
    val btn = view.findViewById<Button>(R.id.btnNext)
    val date = view.findViewById<TextView>(R.id.tvDate)


    fun Bind(property: Property, clickListener:(Property) -> Unit){
        title.text = property.title
        location.text = property.location
        price.text = property.price.toString()
        date.text = property.date

        img.setImageURI(property.image.toUri())

        btn.text = if (isDeletable) "Delete" else "->"


        btn. setOnClickListener{
            clickListener(property)
        }
    }





}