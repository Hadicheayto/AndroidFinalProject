package com.example.tpproject

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tpproject.data.Property
import com.example.tpproject.data.User
import com.example.tpproject.fragments.HomeFragment
import com.example.tpproject.utilities.InjectorUtils
import androidx.lifecycle.Observer
import com.example.tpproject.data.UserManager
import java.io.IOException

class profile : AppCompatActivity() {

    private lateinit var imageAddPhoto: ImageView
    var imageUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        imageAddPhoto = findViewById<ImageView>(R.id.imageAddPhoto)
        val fname = findViewById<EditText>(R.id.etFnameProfile)
        val lname = findViewById<EditText>(R.id.etLnameProfile)
        val email = findViewById<EditText>(R.id.etEmailProfile)
        val password = findViewById<EditText>(R.id.etPasswordProfile)
        val btnSave = findViewById<Button>(R.id.btnSaveProfile)
        val arrowBack = findViewById<ImageView>(R.id.ivArrowBackProfile)
        val btnDeleteImage = findViewById<Button>(R.id.btnDeleteImgg)

        val factory = InjectorUtils.providePropertiesViewModelFactory(this)
        val viewModel = ViewModelProvider(this, factory).get(PropertyViewModel::class.java)



        imageAddPhoto.setOnClickListener{
            pickImageFromGallery()
        }

        btnDeleteImage.setOnClickListener{
            imageAddPhoto.setImageResource(R.drawable.addphotoicon)
            imageUrl = "empty"
        }



        val userrr = viewModel.getUserById(UserManager.getUserId())
        if (userrr != null) {
            imageUrl = userrr.image
        }

        if (userrr != null) {
            if (userrr.image != "empty" && userrr.image != "")
            {
                imageAddPhoto.setImageURI(userrr.image.toUri())
            }
        }
        fname.text = Editable.Factory.getInstance().newEditable(userrr?.fname)
        lname.text = Editable.Factory.getInstance().newEditable(userrr?.lname)
        email.text = Editable.Factory.getInstance().newEditable(userrr?.email)
        password.text = Editable.Factory.getInstance().newEditable(userrr?.password)

        arrowBack.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

//        val btnAddUser = findViewById<Button>(R.id.btnAddUser)
//        val btnGetUser = findViewById<Button>(R.id.btnGetUser)
//
//        val factory = InjectorUtils.providePropertiesViewModelFactory(this)
//        val viewModel = ViewModelProvider(this, factory).get(PropertyViewModel::class.java)

        btnSave.setOnClickListener{

            val userInDb = viewModel.getUserById(UserManager.getUserId())
            val editUser = User(UserManager.getUserId(),fname.text.toString(),lname.text.toString(), email.text.toString(),password.text.toString(),imageUrl,0)

            if (userInDb != null) {
                Log.e("Response", "userindb: ${userInDb.image}")
            }
            Log.e("Response", "edituser: ${editUser.image}")

            if(editUser.fname == "")
            {
                Log.e("Response", "user empty")
            }else
            {
                Log.e("Response", "user is not empty")
            }

            var valid = false;
            if (userInDb != null) {

                if (editUser.fname == "" || editUser.lname == "" || editUser.email == "" || editUser.password == "") {
                    valid = true
                } else {
                    // Check if editUser is the same as userInDb
                    if (editUser.fname == userInDb.fname && editUser.lname == userInDb.lname && editUser.email == userInDb.email && editUser.password == userInDb.password && editUser.image == userInDb.image) {
                        valid = true
                    }
                }

                if(valid)
                {
                    Toast.makeText(this, "edit your info to save", Toast.LENGTH_SHORT).show()
                }else
                {
                    val rowAffected = viewModel.editUser(editUser)
                    if(rowAffected > 0)
                    {
                        Toast.makeText(this, "save successfully", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this, "error saving data", Toast.LENGTH_SHORT).show()
                    }

                }


            }
        }
//
//        btnGetUser.setOnClickListener{
//
//
//            val userrr = viewModel.getUserById(1)
//
//            Log.e("Response", "the user is: $userrr")
//
//        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        pickImage.launch(intent)
    }
    
    private val pickImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val selectedImageUri: Uri? = data?.data
            // Handle the selected image URI here
            try {
                val bitmap = BitmapFactory.decodeStream(this.contentResolver.openInputStream(selectedImageUri!!))
                // Set the bitmap to your ImageView or perform any other actions
                imageAddPhoto.setImageBitmap(bitmap)

                Log.e("Response", "the user is: $bitmap")

                val imagePath = Functions.saveImageToExternalStorage(this,bitmap)

                imageUrl = imagePath

//                if(imageUrl != "")
//                {
//                    errorText.setText("")
//                }


            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun listItemClicked(property: Property) {
        val intent = Intent(this, property_details::class.java)
        intent.putExtra("supplier", "hadi")
        intent.putExtra("phoneNumber", property.phonenumber)
        intent.putExtra("imgUrl", property.image)
        intent.putExtra("title", property.title)
        intent.putExtra("description", property.description)
        intent.putExtra("price", property.price)
        intent.putExtra("location", property.location)
        intent.putExtra("title", property.title)
        startActivity(intent)
    }
}