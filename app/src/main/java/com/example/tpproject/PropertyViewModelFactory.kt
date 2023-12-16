package com.example.tpproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tpproject.data.PropertyRepository

@Suppress("UNCHECKED_CAST")
class PropertyViewModelFactory(private val propertyRepository: PropertyRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PropertyViewModel(propertyRepository) as T
    }
}