package com.example.tpproject.utilities

import android.content.Context
import com.example.tpproject.PropertyViewModelFactory
import com.example.tpproject.data.DataBaseHandler
import com.example.tpproject.data.Repository


//object InjectorUtils {
//
//    fun providedPropertiesViewModelFactory() : PropertyViewModelFactory{
//        val propertyRepository = PropertyRepository.getInstance(fakeDatabase.getInstance().propertyFake)
//        return PropertyViewModelFactory(propertyRepository)
//    }
//}

//object InjectorUtils {
//
//    fun providedPropertiesViewModelFactory(context: Context): PropertyViewModelFactory {
//        val databaseHandler = DataBaseHandler(context)
//        val propertyRepository = PropertyRepository.getInstance(databaseHandler)
//        return PropertyViewModelFactory(propertyRepository)
//    }
//}

object InjectorUtils {

    fun providePropertiesViewModelFactory(context: Context): PropertyViewModelFactory {
        val dataBaseHandler = DataBaseHandler(context)
        val repository = Repository.getInstance(dataBaseHandler)
        return PropertyViewModelFactory(repository)
    }
}
