package com.example.tpproject

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tpproject.data.Property
import com.example.tpproject.data.Repository
import com.example.tpproject.data.User

//class PropertyViewModel(private val propertyRepository: PropertyRepository) : ViewModel() {
//
//    fun getProperty() = propertyRepository.getProperties()
//
//    fun addQuote(property: Property) = propertyRepository.addProperty(property)
//
//
//}

class PropertyViewModel(private val repository: Repository) : ViewModel() {

    private val _properties = MutableLiveData<List<Property>>()
    val properties: LiveData<List<Property>>
        get() = _properties

    init {
        _properties.value = repository.getProperties().value
    }

    fun addProperty(property: Property): Long {
        val id = repository.addProperty(property)
        _properties.value = repository.getProperties().value
        return id;
    }

    fun addUser(user: User):Long {
        return repository.addUser(user)
    }

    fun getUserById(userId: Long): User? {
        return repository.getUserById(userId)
    }

    fun editUser(user: User): Int {
        return repository.editUser(user)
    }

    fun getPropertiesByUserId(userId: Long): List<Property> {
        return repository.getPropertiesByUserId(userId)
    }

    fun getUserByEmailAndPassword(email: String, password: String): User? {
        return repository.getUserByEmailAndPassword(email, password)
    }

    fun deleteAllProperties(): Int {
        return  repository.deleteAllProperties()
    }

    fun deleteAllUsers(): Int {
        return  repository.deleteAllProperties()
    }

    fun deletePropertyById(propertyId: Long): Int {
        return repository.deletePropertyById(propertyId)
    }

}
