package com.example.tpproject

import androidx.lifecycle.ViewModel
import com.example.tpproject.data.Property
import com.example.tpproject.data.PropertyRepository

class PropertyViewModel(private val propertyRepository: PropertyRepository) : ViewModel() {

    fun getProperty() = propertyRepository.getProperties()

    fun addQuote(property: Property) = propertyRepository.addProperty(property)


}