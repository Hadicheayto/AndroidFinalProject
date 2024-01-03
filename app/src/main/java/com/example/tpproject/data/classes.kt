package com.example.tpproject.data

//data class Property(val supplier:String, val phoneNumber:Int,val title:String,val description:String,val location:String,val price:Int,val image:Int){
//
//}

data class Property(
    val id: Long,
    val user_id: Int,
    val date: String,
    val title: String,
    val description: String,
    val location: String,
    val price: Long,
    val image: String,
    val supplier: String,
    val phonenumber: Int,
    val active: Int
) {
    // Add a no-argument constructor
    constructor() : this(
        id = 0,
        user_id = 0,
        date = "",
        title = "",
        description = "",
        location = "",
        price = 0,
        image = "",
        supplier = "",
        phonenumber = 0,
        active = 0
    )
}



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

data class Preference(
    val id: Long,
    val userId: Long,
    val type: String,
    val location: String,
    val budget: String,
    val capacity: String,
){
    // No-argument constructor for Firebase
    constructor() : this(0, 0, "", "", "", "")
}

object UserManager {
    private var userId: Long = -1 // Default value when no user is signed in

    fun setUserId(id: Long) {
        userId = id
    }

    fun getUserId(): Long {
        return userId
    }

}

data class News(val title: String, val description: String, val imageURL: String, val date: String)
