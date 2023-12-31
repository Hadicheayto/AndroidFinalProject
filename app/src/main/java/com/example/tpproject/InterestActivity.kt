package com.example.tpproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.example.tpproject.data.Preference
import com.example.tpproject.data.UserManager
import com.example.tpproject.fragments.HomeFragment

class InterestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interest)


        val spinnerType = findViewById<Spinner>(R.id.spinnerType)
        val spinnerLocation = findViewById<Spinner>(R.id.spinnerLocation)
        val spinnerBudget = findViewById<Spinner>(R.id.spinnerBudget)
        val spinnerCapacity = findViewById<Spinner>(R.id.spinnerCapacity)
        val btnContinue = findViewById<Button>(R.id.btnContinue)
        val btnSkip = findViewById<Button>(R.id.btnSkip)



        val itemsType = listOf("type", "villa", "apartment", "kabana")
        val itemsLocation = listOf("location","beirut","jnoub","jbeil")
        val itemsBudget = listOf("Budget", "0-50000$", "50000-100000$", "100000-200000$","200000-300000$","300000-400000$","400000-500000$","500000-1000000$",">1000000$ ")
        val itemsCapacity = listOf("Capacity","0-100m","100-200m","200-300m","300-400m","400-500m",">500m")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, itemsType)
        val adapterLocation = ArrayAdapter(this, android.R.layout.simple_spinner_item, itemsLocation)
        val adapterBudget= ArrayAdapter(this, android.R.layout.simple_spinner_item, itemsBudget)
        val adapterCapacity= ArrayAdapter(this, android.R.layout.simple_spinner_item, itemsCapacity)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterLocation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterBudget.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterCapacity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerType.adapter = adapter
        spinnerLocation.adapter = adapterLocation
        spinnerBudget.adapter = adapterBudget
        spinnerCapacity.adapter = adapterCapacity

        spinnerType.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Handle the selected item here
                val selectedItem = itemsType[position]
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

        spinnerBudget.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Handle the selected item here
                val selectedItem = itemsBudget[position]
                // Do something with the selected item
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle the case where nothing is selected (optional)
            }
        })

        spinnerCapacity.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Handle the selected item here
                val selectedItem = itemsCapacity[position]
                // Do something with the selected item
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle the case where nothing is selected (optional)
            }
        })

        btnContinue.setOnClickListener{



            val selectedTypePosition = spinnerType.selectedItemPosition
            val selectedType = if (selectedTypePosition != AdapterView.INVALID_POSITION) {
                spinnerType.adapter.getItem(selectedTypePosition).toString()
            } else {
                ""
            }

            val selectedLocationPosition = spinnerLocation.selectedItemPosition
            val selectedLocation = if (selectedLocationPosition != AdapterView.INVALID_POSITION) {
                spinnerLocation.adapter.getItem(selectedLocationPosition).toString()
            } else {
                ""
            }

            val selectedBudgetPosition = spinnerBudget.selectedItemPosition
            val selectedBudget= if (selectedBudgetPosition != AdapterView.INVALID_POSITION) {
                spinnerBudget.adapter.getItem(selectedBudgetPosition).toString()
            } else {
                ""
            }

            val selectedCapacityPosition = spinnerCapacity.selectedItemPosition
            val selectedCapacity= if (selectedCapacityPosition != AdapterView.INVALID_POSITION) {
                spinnerCapacity.adapter.getItem(selectedCapacityPosition).toString()
            } else {
                ""
            }

            val preference = Preference(0, UserManager.getUserId(),selectedType,selectedLocation,selectedBudget,selectedCapacity)

            Log.e("Response", "preference=: $preference")

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnSkip.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }










    }
}