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

    fun addProperty(property: Property) {
        dataBaseHandler.insertProperties(property)
    }

    fun getProperties() = MutableLiveData<List<Property>>().apply {
        value = dataBaseHandler.getAllProperties()
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

