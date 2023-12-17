package com.example.tpproject

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tpproject.data.Property
import com.example.tpproject.data.PropertyRepository

//class PropertyViewModel(private val propertyRepository: PropertyRepository) : ViewModel() {
//
//    fun getProperty() = propertyRepository.getProperties()
//
//    fun addQuote(property: Property) = propertyRepository.addProperty(property)
//
//
//}

class PropertyViewModel(private val propertyRepository: PropertyRepository) : ViewModel() {

    private val _properties = MutableLiveData<List<Property>>()
    val properties: LiveData<List<Property>>
        get() = _properties

    init {
        _properties.value = propertyRepository.getProperties().value
    }

    fun addProperty(property: Property) {
        propertyRepository.addProperty(property)
        _properties.value = propertyRepository.getProperties().value
    }
}
