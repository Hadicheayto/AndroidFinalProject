package com.example.tpproject.data

import android.text.Editable

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
    val price: Long,
    val image: String,
    val supplier:String,
    val phonenumber:Int,
    val active: Int
)


data class User(
    val id: Long,
    val fname: String,
    val lname: String,
    val email: String,
    val password: String,
    val image: String,
    val active: Int
    // Add other user-related properties as needed
)

object UserManager {
    private var userId: Long = -1 // Default value when no user is signed in

    fun setUserId(id: Long) {
        userId = id
    }

    fun getUserId(): Long {
        return userId
    }

}

