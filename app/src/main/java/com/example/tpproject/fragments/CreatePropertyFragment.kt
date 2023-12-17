package com.example.tpproject.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
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
import androidx.fragment.app.Fragment
import com.example.tpproject.R
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.example.tpproject.PropertyViewModel
import com.example.tpproject.data.Property
import com.example.tpproject.utilities.InjectorUtils
import com.google.android.material.textfield.TextInputLayout


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

    private lateinit var imageAddPhoto: ImageView

    private val pickImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            if (result != null) {
                imageAddPhoto.setImageURI(result)
                // You can save the image URI or perform other operations here
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_property_create, container, false)

        // code for the dropdown butt
        val items = listOf("title", "villa", "apartment", "kabana")
        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, items)
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Find the Spinner in your layout and apply the adapter
        val dropdown = view.findViewById<Spinner>(R.id.spinnerTitle)  // Replace with your actual spinner ID
        dropdown.adapter = adapter
        // Set a listener to handle item selection
        dropdown.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Handle the selected item here
                val selectedItem = items[position]
                // Do something with the selected item
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle the case where nothing is selected (optional)
            }
        })


        val imageAddPhoto = view.findViewById<ImageView>(R.id.imageAddPhoto)

        // Set click listener to open the gallery
        imageAddPhoto.setOnClickListener {
            openFilePicker()
        }


        val buttonAddProperty = view.findViewById<Button>(R.id.btnAddProperty)

        // Set click listener to validate the form and add property
        buttonAddProperty.setOnClickListener {

            val property = Property(
                title = "home",
                description = "i like this home",
                location = "beirut",
                price = 10000.0,
                active = 1,
                date = "10/10/10",
                id = 1,
                image = "url",
                user_id = 1
            )

            // Add the property to the database using your ViewModel
            val factory = InjectorUtils.providePropertiesViewModelFactory(requireContext())
            val viewModel = ViewModelProvider(this, factory).get(PropertyViewModel::class.java)

            viewModel.addProperty(property)

            // Display a success message
            Toast.makeText(requireContext(), "Property added successfully", Toast.LENGTH_SHORT).show()


            if (validateForm(view)) {


                // Create a Property object

            }
        }


        return view
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true) // Ensure local content only
        pickImage.launch(intent.toString())
    }

    private fun validateForm(view: View): Boolean {
        var isValid = true

        // Validate Description
        val descriptionEditText = view.findViewById<EditText>(R.id.edDiscription)
        val descriptionContainer = view.findViewById<TextInputLayout>(R.id.descriptionContainer)
        if (descriptionEditText.text.toString().trim().isEmpty()) {
            descriptionContainer.error = "Description is required"
            isValid = false

        } else {
            descriptionContainer.error = null
        }

        val locationEditText = view.findViewById<EditText>(R.id.edLocation)
        val locationInputLayout = view.findViewById<TextInputLayout>(R.id.locationContainer)
        if (locationEditText.text.toString().trim().isEmpty()) {
            locationInputLayout.error = "Location is required"
            isValid = false
        } else {
            locationInputLayout.error = null
        }

        // Validate Price
        val priceEditText = view.findViewById<EditText>(R.id.edPrice)
        val priceInputLayout = view.findViewById<TextInputLayout>(R.id.priceContainer)
        if (priceEditText.text.toString().trim().isEmpty()) {
            priceInputLayout.error = "Price is required"
            isValid = false
        } else {
            priceInputLayout.error = null
        }

        // Validate Supplier Name
        val supplierNameEditText = view.findViewById<EditText>(R.id.edSupplierName)
        val supplierNameInputLayout = view.findViewById<TextInputLayout>(R.id.supplierContainer)
        if (supplierNameEditText.text.toString().trim().isEmpty()) {
            supplierNameInputLayout.error = "Supplier name is required"
            isValid = false
        } else {
            supplierNameInputLayout.error = null
        }

        // Validate Phone Number
        val phoneNumberEditText = view.findViewById<EditText>(R.id.edPhoneNumber)
        val phoneNumberInputLayout = view.findViewById<TextInputLayout>(R.id.phonenumberContainer)
        if (phoneNumberEditText.text.toString().trim().isEmpty()) {
            phoneNumberInputLayout.error = "Phone number is required"
            isValid = false
        } else {
            phoneNumberInputLayout.error = null
        }

        // Validate Spinner

        val spinnerTitle = view.findViewById<Spinner>(R.id.spinnerTitle)
        val spinnerInputLayout = view.findViewById<TextInputLayout>(R.id.spinnerContainer)

// Assuming "title" is the default value
        val selectedTitlePosition = spinnerTitle.selectedItemPosition
        val selectedTitle = if (selectedTitlePosition != AdapterView.INVALID_POSITION) {
            spinnerTitle.adapter.getItem(selectedTitlePosition).toString()
        } else {
            ""
        }
        Log.e("Response", "get meme data: $selectedTitle")

        if (selectedTitle == "title") {
            val errorText = "Please select a valid title"
            val errorView = spinnerTitle.selectedView as TextView
            errorView.error = errorText
            errorView.setTextColor(Color.RED)  // Optionally, you can change the text color for better visibility
            isValid = false

        } else {
            val errorView = spinnerTitle.selectedView as TextView
            errorView.error = null
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
