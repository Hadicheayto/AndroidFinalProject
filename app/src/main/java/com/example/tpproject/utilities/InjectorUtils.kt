package com.example.tpproject.utilities

import com.example.tpproject.PropertyViewModel
import com.example.tpproject.PropertyViewModelFactory
import com.example.tpproject.data.PropertyRepository
import com.example.tpproject.data.fakeDatabase

object InjectorUtils {

    fun providedPropertiesViewModelFactory() : PropertyViewModelFactory{
        val propertyRepository = PropertyRepository.getInstance(fakeDatabase.getInstance().propertyFake)
        return PropertyViewModelFactory(propertyRepository)
    }
}