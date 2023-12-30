package com.example.tpproject

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tpproject.data.Property
import com.example.tpproject.data.PropertyRepository
import com.example.tpproject.data.TABLE_NAME
import com.example.tpproject.data.User

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

    fun addProperty(property: Property): Long {
        val id = propertyRepository.addProperty(property)
        _properties.value = propertyRepository.getProperties().value
        return id;
    }

    fun addUser(user: User):Long {
        return propertyRepository.addUser(user)
    }

    fun getUserById(userId: Long): User? {
        return propertyRepository.getUserById(userId)
    }

    fun editUser(user: User): Int {
        return propertyRepository.editUser(user)
    }

    fun getPropertiesByUserId(userId: Long): List<Property> {
        return propertyRepository.getPropertiesByUserId(userId)
    }

    fun getUserByEmailAndPassword(email: String, password: String): User? {
        return propertyRepository.getUserByEmailAndPassword(email, password)
    }

    fun deleteAllProperties(): Int {
        return  propertyRepository.deleteAllProperties()
    }

    fun deleteAllUsers(): Int {
        return  propertyRepository.deleteAllProperties()
    }

    fun deletePropertyById(propertyId: Long): Int {
        return propertyRepository.deletePropertyById(propertyId)
    }

}
