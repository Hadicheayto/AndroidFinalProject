package com.example.tpproject.data

class PropertyRepository private constructor(private val propertyFake : fakeProperty){

    fun addProperty(property: Property)
    {
        propertyFake.addProperty(property)
    }

    fun getProperties() = propertyFake.getProperties()


    companion object{
        @Volatile private var instance: PropertyRepository? = null

        fun getInstance(propertyFake : fakeProperty) =
            instance ?: synchronized(this) {
                instance ?: PropertyRepository(propertyFake).also { instance = it }
            }

    }
}