package com.example.tpproject.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


//class PropertyRepository private constructor(private val propertyFake : fakeProperty){
//
//    fun addProperty(property: Property)
//    {
//        propertyFake.addProperty(property)
//    }
//
//    fun getProperties() = propertyFake.getProperties()
//
//
//    companion object{
//        @Volatile private var instance: PropertyRepository? = null
//
//        fun getInstance(propertyFake : fakeProperty) =
//            instance ?: synchronized(this) {
//                instance ?: PropertyRepository(propertyFake).also { instance = it }
//            }
//
//    }
//}

//class PropertyRepository private constructor(private val databaseHandler: DataBaseHandler) {
//
//    fun addProperty(property: Property) {
//        databaseHandler.insertProperties(property)
//    }
//
//    fun getProperties() = databaseHandler.getAllProperties()
//
//    companion object {
//        @Volatile private var instance: PropertyRepository? = null
//
//        fun getInstance(databaseHandler: DataBaseHandler) =
//            instance ?: synchronized(this) {
//                instance ?: PropertyRepository(databaseHandler).also { instance = it }
//            }
//    }
//}

class Repository private constructor(private val dataBaseHandler: DataBaseHandler) {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var databaseReferencePreferance: DatabaseReference

    init {
        initializeFirebase()


    }

    private fun initializeFirebase() {
        databaseReference = FirebaseDatabase.getInstance().getReference("properties")
        databaseReferencePreferance = FirebaseDatabase.getInstance().getReference("preferences")
    }

//    fun addProperty(property: Property): Long {
//        return dataBaseHandler.insertProperties(property)
//    }

//    fun getProperties() = MutableLiveData<List<Property>>().apply {
//        value = dataBaseHandler.getAllProperties()
//    }

    fun insertPropertyToFirebase(property: Property): Int {
        val propertyKey = databaseReference.push().key
        Log.e("Repository", "propertyKey: ${propertyKey}")
        return if (propertyKey != null) {
            databaseReference.child(propertyKey).setValue(property).addOnCompleteListener {
                Log.e("Repository", "Property added successfully in Firebase")
            }.addOnFailureListener { err ->
                Log.e("Repository", "Failed to add property in Firebase: ${err.message}", err)
            }
            // Return 1 for success
            return 1
        } else {
            // Return 0 for failure
            return 0
        }
    }

    fun getProperties(): MutableLiveData<List<Property>> {
        val propertiesLiveData = MutableLiveData<List<Property>>()

        // Add a listener to fetch data from Firebase and update the LiveData
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val propertiesList = mutableListOf<Property>()

                for (propertySnapshot in snapshot.children) {
                    val property = propertySnapshot.getValue(Property::class.java)
                    property?.let { propertiesList.add(it) }
                }

                propertiesLiveData.value = propertiesList
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors here
                // You can log the error or take other actions
                // For example, propertiesLiveData.value = emptyList() to indicate an error
            }
        })

        return propertiesLiveData
    }

    //Add a method to insert a user
    fun addUser(user: User) : Long  {
        return dataBaseHandler.insertUser(user)
    }

     //Add a method to get a user by ID
    fun getUserById(userId: Long): User? {
        return dataBaseHandler.getUserById(userId)
    }

    fun editUser(user: User): Int {
        return dataBaseHandler.editUser(user)
    }

//    fun getPropertiesByUserId(userId: Long): List<Property> {
//        return dataBaseHandler.getPropertiesByUserId(userId)
//    }
    fun getPropertiesByUserId(userId: Long, callback: (List<Property>) -> Unit) {
        val propertiesList: MutableList<Property> = mutableListOf()

        // Query Firebase for properties with the specified user ID
        databaseReference.orderByChild("user_id").equalTo(userId.toDouble())
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (propertySnapshot in snapshot.children) {
                        val property = propertySnapshot.getValue(Property::class.java)
                        property?.let { propertiesList.add(it) }
                    }
                    callback(propertiesList)
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle the error, if needed
                }
            })
    }

    fun getUserByEmailAndPassword(email: String, password: String): User? {
        return dataBaseHandler.getUserByEmailAndPassword(email, password)
    }

//    fun deleteAllProperties(): Int {
//        return dataBaseHandler.deleteAllProperties()
//    }

    fun deleteAllUsers(): Int {
        return dataBaseHandler.deleteAllUsers()
    }

//    fun deletePropertyById(propertyId: Long): Int {
//        return dataBaseHandler.deletePropertyById(propertyId)
//    }
    fun deletePropertyById(Id: Long): Int {
        // Query Firebase for properties with the specified user ID
        databaseReference.orderByChild("id").equalTo(Id.toDouble())
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (propertySnapshot in snapshot.children) {
                        // Delete each property that matches the condition
                        val propertyId = propertySnapshot.key
                        propertyId?.let {
                            val propertyReference = databaseReference.child(it)
                            propertyReference.removeValue().addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.e("Repository", "Property deleted successfully from Firebase")
                                } else {
                                    Log.e("Repository", "Failed to delete property from Firebase: ${task.exception?.message}", task.exception)
                                }
                            }.addOnFailureListener { exception ->
                                Log.e("Repository", "Exception during property deletion: ${exception.message}", exception)
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle the error, if needed
                }
            })

        // Return 1 for success
        return 1
    }

    fun insertPreferenceToFirebase(preference: Preference): Int {
        val preferenceKey = databaseReferencePreferance.push().key

        Log.e("Repository", "preferenceKey: $preferenceKey")

        return if (preferenceKey != null) {
            databaseReferencePreferance.child(preferenceKey).setValue(preference)
                .addOnCompleteListener {
                    Log.e("Repository", "Preference added successfully in Firebase")
                }.addOnFailureListener { err ->
                    Log.e("Repository", "Failed to add preference in Firebase: ${err.message}", err)
                }
            // Return 1 for success
            1
        } else {
            // Return 0 for failure
            0
        }
    }

    fun getPreferencesByUserId(userId: Long, callback: (List<Preference>) -> Unit) {
        val preferenceList: MutableList<Preference> = mutableListOf()

        // Query Firebase for properties with the specified user ID
        databaseReferencePreferance.orderByChild("userId").equalTo(userId.toDouble())
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (propertySnapshot in snapshot.children) {
                        val property = propertySnapshot.getValue(Preference::class.java)
                        property?.let { preferenceList.add(it) }
                    }
                    callback(preferenceList)
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle the error, if needed
                }
            })
    }




    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(dataBaseHandler: DataBaseHandler) =
            instance ?: synchronized(this) {
                instance ?: Repository(dataBaseHandler).also { instance = it }
            }
    }
}

