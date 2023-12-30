package com.example.tpproject.data

import androidx.lifecycle.MutableLiveData

//class PropertyRepository private constructor(private val propertyFake : fakeProperty){
//
//    fun addProperty(property: Property)
//    {
//        propertyFake.addProperty(property)
//    }
//
//    fun getProperties() = propertyFake.getProperties()
//
//
//    companion object{
//        @Volatile private var instance: PropertyRepository? = null
//
//        fun getInstance(propertyFake : fakeProperty) =
//            instance ?: synchronized(this) {
//                instance ?: PropertyRepository(propertyFake).also { instance = it }
//            }
//
//    }
//}

//class PropertyRepository private constructor(private val databaseHandler: DataBaseHandler) {
//
//    fun addProperty(property: Property) {
//        databaseHandler.insertProperties(property)
//    }
//
//    fun getProperties() = databaseHandler.getAllProperties()
//
//    companion object {
//        @Volatile private var instance: PropertyRepository? = null
//
//        fun getInstance(databaseHandler: DataBaseHandler) =
//            instance ?: synchronized(this) {
//                instance ?: PropertyRepository(databaseHandler).also { instance = it }
//            }
//    }
//}

class PropertyRepository private constructor(private val dataBaseHandler: DataBaseHandler) {

    fun addProperty(property: Property): Long {
        return dataBaseHandler.insertProperties(property)
    }

    fun getProperties() = MutableLiveData<List<Property>>().apply {
        value = dataBaseHandler.getAllProperties()
    }

     //Add a method to insert a user
    fun addUser(user: User) : Long  {
        return dataBaseHandler.insertUser(user)
    }

     //Add a method to get a user by ID
    fun getUserById(userId: Long): User? {
        return dataBaseHandler.getUserById(userId)
    }

    fun editUser(user: User): Int {
        return dataBaseHandler.editUser(user)
    }

    fun getPropertiesByUserId(userId: Long): List<Property> {
        return dataBaseHandler.getPropertiesByUserId(userId)
    }

    fun getUserByEmailAndPassword(email: String, password: String): User? {
        return dataBaseHandler.getUserByEmailAndPassword(email, password)
    }

    fun deleteAllProperties(): Int {
        return dataBaseHandler.deleteAllProperties()
    }

    fun deleteAllUsers(): Int {
        return dataBaseHandler.deleteAllUsers()
    }

    fun deletePropertyById(propertyId: Long): Int {

        return dataBaseHandler.deletePropertyById(propertyId)
    }

    companion object {
        @Volatile
        private var instance: PropertyRepository? = null

        fun getInstance(dataBaseHandler: DataBaseHandler) =
            instance ?: synchronized(this) {
                instance ?: PropertyRepository(dataBaseHandler).also { instance = it }
            }
    }
}

