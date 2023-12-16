package com.example.tpproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tpproject.data.Property

class RecycleViewAdapter(
    private val propertyList:List<Property>,
    private val clickListener:(Property) -> Unit
) : RecyclerView.Adapter<MyViewHolder>(){

    private var filteredList: List<Property> = propertyList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.list_item,parent,false)
        return MyViewHolder(listItem)
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

class MyViewHolder(val view:View): RecyclerView.ViewHolder(view){

    val title = view.findViewById<TextView>(R.id.tvTitle)
    val location  = view.findViewById<TextView>(R.id.tvLocation)
    val price  = view.findViewById<TextView>(R.id.tvPrice)
    val img = view.findViewById<ImageView>(R.id.ivImageProperty)
    val btn = view.findViewById<Button>(R.id.btnNext)

    fun Bind(property: Property, clickListener:(Property) -> Unit){
        title.text = property.title
        location.text = property.location
        price.text = property.price.toString()
        img.setImageResource(property.image)
//        Glide.with(img.context)
//            .load(property.image) // Assuming property.image is a URL
//            .into(img)
        btn. setOnClickListener{
            clickListener(property)
        }
    }





}