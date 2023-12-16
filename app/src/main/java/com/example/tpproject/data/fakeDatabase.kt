package com.example.tpproject.data

class fakeDatabase private constructor() {

    var propertyFake = fakeProperty()
        private  set

    companion object{
        @Volatile private var instance: fakeDatabase? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: fakeDatabase().also { instance = it }
            }

    }
}