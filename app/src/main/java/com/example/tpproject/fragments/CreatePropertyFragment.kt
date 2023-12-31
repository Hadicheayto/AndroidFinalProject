package com.example.tpproject.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tpproject.Functions
import com.example.tpproject.PropertyViewModel
import com.example.tpproject.R
import com.example.tpproject.data.Property
import com.example.tpproject.data.UserManager
import com.example.tpproject.utilities.InjectorUtils
import com.google.android.material.textfield.TextInputLayout
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CreatePropertyFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }



    var spinnerTitleResult = ""
    var spinnerLocationResult = ""
    private lateinit var imageAddPhoto: ImageView
    var imageUrl = "";
    private  lateinit var errorText: TextView
    private lateinit var pickImage: ActivityResultLauncher<Intent>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_property_create, container, false)

        errorText = view.findViewById<TextView>(R.id.TvErrorImage)
        imageAddPhoto = view.findViewById<ImageView>(R.id.imageAddPhoto)
        val spinnerTitle = view.findViewById<Spinner>(R.id.spinnerTitle)
        val descriptionEditText = view?.findViewById<EditText>(R.id.edDiscription)
        val spinnerLocation = view.findViewById<Spinner>(R.id.spinnerTitleLocation)
//        val locationEditText = view?.findViewById<EditText>(R.id.edLocation)
        val priceEditText = view?.findViewById<EditText>(R.id.edPrice)
        val supplierNameEditText = view?.findViewById<EditText>(R.id.edSupplierName)
        val phoneNumberEditText = view?.findViewById<EditText>(R.id.edPhoneNumber)
        val buttonAddProperty = view.findViewById<Button>(R.id.btnAddProperty)

        // code for the dropdown butt
        val items = listOf("title", "villa", "apartment", "kabana")
        val itemsLocation = listOf("location","beirut","jnoub","jbeil")
        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, items)
        val adapterLocation = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, itemsLocation)
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterLocation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Find the Spinner in your layout and apply the adapter
         // Replace with your actual spinner ID
        spinnerTitle.adapter = adapter
        spinnerLocation.adapter = adapterLocation
        // Set a listener to handle item selection
        spinnerTitle.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Handle the selected item here
                val selectedItem = items[position]
                // Do something with the selected item
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle the case where nothing is selected (optional)
            }
        })

        spinnerLocation.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Handle the selected item here
                val selectedItem = itemsLocation[position]
                // Do something with the selected item
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle the case where nothing is selected (optional)
            }
        })

        pickImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val selectedImageUri: Uri? = data?.data
                // Handle the selected image URI here
                try {
                    val bitmap =
                        BitmapFactory.decodeStream(requireContext().contentResolver.openInputStream(selectedImageUri!!))
                    // Set the bitmap to your ImageView or perform any other actions
                    imageAddPhoto.setImageBitmap(bitmap)

                    Log.e("Response", "the user is: $bitmap")

                    val imagePath = Functions.saveImageToExternalStorage(requireContext(), bitmap)

                    imageUrl = imagePath

                    if (imageUrl != "") {
                        errorText.setText("")
                    }

                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }




        // Set click listener to open the gallery
        imageAddPhoto.setOnClickListener {
            pickImageFromGallery()
        }





        // Set click listener to validate the form and add property
        buttonAddProperty.setOnClickListener {
            if (validateForm(
                    descriptionEditText,
                    spinnerLocation,
                    priceEditText,
                    supplierNameEditText,
                    phoneNumberEditText,
                    spinnerTitle,
                    view
                )
            ) {

                val currentDate = LocalDate.now()
                val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val formattedDate = currentDate.format(dateFormat)
                imageAddPhoto.setImageResource(R.drawable.addphotoicon)

                val property = Property(
                    title = spinnerTitleResult,
                    description = descriptionEditText?.text.toString(),
                    location = spinnerLocationResult,
                    price = priceEditText?.text.toString().toLong(),
                    active = 1,
                    date = formattedDate,
                    id = 1,
                    image = imageUrl,
                    user_id = UserManager.getUserId().toInt(),
                    supplier = supplierNameEditText?.text.toString(),
                    phonenumber = phoneNumberEditText?.text.toString().toInt()
                )

                //Log.e("Response", "get meme data: $property")

                // Add the property to the database using your ViewModel
                val factory = InjectorUtils.providePropertiesViewModelFactory(requireContext())
                val viewModel = ViewModelProvider(this, factory).get(PropertyViewModel::class.java)
                val addedProperty = viewModel.addProperty(property)
                if(addedProperty > 0)
                {
                    Toast.makeText(requireContext(), "Property added successfully", Toast.LENGTH_SHORT).show()
                    descriptionEditText?.setText("")
                    spinnerLocation.setSelection(0)
                    priceEditText?.setText("")
                    supplierNameEditText?.setText("")
                    phoneNumberEditText?.setText("")
                    spinnerTitle.setSelection(0)


                }else
                {
                    Toast.makeText(requireContext(), "error adding property", Toast.LENGTH_SHORT).show()
                }

            }
        }


        return view
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        pickImage.launch(intent)
    }


    private fun validateForm(
        descriptionEditText: EditText?,
        spinnerLocation: Spinner,
        priceEditText: EditText?,
        supplierNameEditText: EditText?,
        phoneNumberEditText: EditText?,
        spinnerTitle:Spinner,
        view: View
    ): Boolean {
        var isValid = true



        if (imageUrl == "") {
            errorText.text = "Pick image is required"
            isValid = false
        } else {
            errorText.text = ""
        }


        // Validate Description
        val descriptionContainer = view.findViewById<TextInputLayout>(R.id.descriptionContainer)
        if (descriptionEditText?.text.toString().trim().isEmpty()) {
            descriptionContainer.error = "Description is required"
            isValid = false
        } else {
            descriptionContainer.error = null
            Log.e("Response", "description: ${descriptionEditText?.text}")
        }

        val selectedLocationPosition = spinnerLocation.selectedItemPosition
        val selectedLocation = if (selectedLocationPosition != AdapterView.INVALID_POSITION) {
            spinnerLocation.adapter.getItem(selectedLocationPosition).toString()
        } else {
            ""
        }

        if (selectedLocation == "location") {
            val errorText = "Please select a valid location"
            val errorView = spinnerLocation.selectedView as TextView
            errorView.error = errorText
            errorView.setTextColor(Color.RED)  // Optionally, you can change the text color for better visibility
            isValid = false

        } else {
            val errorView = spinnerLocation.selectedView as TextView
            errorView.error = null
            spinnerLocationResult = selectedLocation
        }

        // Validate Location
//        val locationInputLayout = view.findViewById<TextInputLayout>(R.id.locationContainer)
//        if (locationEditText?.text.toString().trim().isEmpty()) {
//            locationInputLayout.error = "Location is required"
//            isValid = false
//        } else {
//            locationInputLayout.error = null
//        }

        // Validate Price
        val priceInputLayout = view.findViewById<TextInputLayout>(R.id.priceContainer)
        if (priceEditText?.text.toString().trim().isEmpty()) {
            priceInputLayout.error = "Price is required"
            isValid = false
        } else {
            priceInputLayout.error = null
        }

        // Validate Supplier Name
        val supplierNameInputLayout = view.findViewById<TextInputLayout>(R.id.supplierContainer)
        if (supplierNameEditText?.text.toString().trim().isEmpty()) {
            supplierNameInputLayout.error = "Supplier name is required"
            isValid = false
        } else {
            supplierNameInputLayout.error = null
        }

        // Validate Phone Number
        val phoneNumberInputLayout = view.findViewById<TextInputLayout>(R.id.phonenumberContainer)
        if (phoneNumberEditText?.text.toString().trim().isEmpty()) {
            phoneNumberInputLayout.error = "Phone number is required"
            isValid = false
        } else {
            phoneNumberInputLayout.error = null
        }

        // Validate Spinner
        //val spinnerTitle = view.findViewById<Spinner>(R.id.spinnerTitle)
        val selectedTitlePosition = spinnerTitle.selectedItemPosition
        val selectedTitle = if (selectedTitlePosition != AdapterView.INVALID_POSITION) {
            spinnerTitle.adapter.getItem(selectedTitlePosition).toString()
        } else {
            ""
        }

        if (selectedTitle == "title") {
            val errorText = "Please select a valid title"
            val errorView = spinnerTitle.selectedView as TextView
            errorView.error = errorText
            errorView.setTextColor(Color.RED)  // Optionally, you can change the text color for better visibility
            isValid = false

        } else {
            val errorView = spinnerTitle.selectedView as TextView
            errorView.error = null
            spinnerTitleResult = selectedTitle
        }

        return isValid
    }




    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
