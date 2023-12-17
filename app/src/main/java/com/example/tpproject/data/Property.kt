package com.example.tpproject.data

import android.widget.ImageView
import java.time.LocalTime
import java.util.Date

//data class Property(val supplier:String, val phoneNumber:Int,val title:String,val description:String,val location:String,val price:Int,val image:Int){
//
//}

data class Property(
    val id: Long, // Assuming id is of type Long, adjust if needed
    val user_id: Int,
    val date: String,
    val title: String,
    val description: String,
    val location: String,
    val price: Double,
    val image: String,
    val active: Int
)
