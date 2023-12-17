package com.example.tpproject.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tpproject.R

//class fakeProperty {
//
//    private  val propertyList = mutableListOf<Property>(
//        Property("hadi cheayto", 81640833, "villa", "villa sur mer sur la poiramide et voila", "rawche", 300000, R.drawable.property1),
//        Property("hadi cheayto", 81640833, "house", "big house", "rawche", 18000, R.drawable.property2),
//        Property("hadi cheayto", 81640833, "apartment", "large apartment", "rawche", 15000, R.drawable.property3),
//        Property("hadi cheayto", 81640833, "cabana", "cabana sur mer", "rawche", 90000, R.drawable.property2),
//
//    )
//    private  val properties = MutableLiveData<List<Property>>()
//
//    init {
//        properties.value = propertyList
//    }
//
//    fun addProperty(property: Property){
//        propertyList.add(property)
//        properties.value = propertyList
//    }
//
//    fun getProperties() = properties as LiveData<List<Property>>
//
//}

//class fakeProperty(private val databaseHandler: DataBaseHandler) {
//
//    fun addProperty(property: Property) {
//        databaseHandler.insertProperties(property)
//    }
//
//    fun getProperties() = databaseHandler.getAllProperties()
//}

//class fakeProperty(private val databaseHandler: DataBaseHandler) {
//
//    interface DataChangeListener {
//        fun onDataChanged(properties: List<Property>)
//    }
//
//    private val dataChangeListeners = mutableListOf<DataChangeListener>()
//
//    fun addDataChangeListener(listener: DataChangeListener) {
//        dataChangeListeners.add(listener)
//    }
//
//    fun removeDataChangeListener(listener: DataChangeListener) {
//        dataChangeListeners.remove(listener)
//    }
//
//    fun notifyDataChanged(properties: List<Property>) {
//        dataChangeListeners.forEach { it.onDataChanged(properties) }
//    }
//
//    fun addProperty(property: Property) {
//        databaseHandler.insertProperties(property)
//        notifyDataChanged(getProperties())
//    }
//
//    fun getProperties() = databaseHandler.getAllProperties()
//}

//class fakeProperty(private val databaseHandler: DataBaseHandler) {
//
//    private val properties = MutableLiveData<List<Property>>()
//
//    init {
//        properties.value = databaseHandler.getAllProperties()
//    }
//
//    fun addProperty(property: Property) {
//        databaseHandler.insertProperties(property)
//        properties.value = databaseHandler.getAllProperties()
//    }
//
//    fun getProperties() = properties as LiveData<List<Property>>
//}
