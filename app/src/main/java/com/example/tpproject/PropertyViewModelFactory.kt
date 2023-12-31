package com.example.tpproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tpproject.data.Repository

@Suppress("UNCHECKED_CAST")
class PropertyViewModelFactory(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PropertyViewModel(repository) as T
    }
}