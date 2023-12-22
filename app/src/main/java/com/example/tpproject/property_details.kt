package com.example.tpproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri

class property_details : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_details)
        val imgUrl = intent.getStringExtra("imgUrl")
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val price = intent.getLongExtra("price", 0)
        val location = intent.getStringExtra("location")
        val supplier = intent.getStringExtra("supplier")
        val phoneNumber = intent.getIntExtra("phoneNumber",0)

        val imgUrlName = findViewById<ImageView>(R.id.Image_details)
        val titleName = findViewById<TextView>(R.id.Title_details)
        val descriptionName = findViewById<TextView>(R.id.Description_details)
        val priceName = findViewById<TextView>(R.id.Price_details)
        val locationName = findViewById<TextView>(R.id.Location_details)
        val supplierName = findViewById<TextView>(R.id.Supplier_details)
        val phoneNumberName = findViewById<TextView>(R.id.PhoneNumber_details)

        if (imgUrl != null) {
            imgUrlName.setImageURI(imgUrl.toUri())
        }
        titleName.text = title
        descriptionName.text = description
        priceName.text = price.toString()
        locationName.text = location
        supplierName.text = supplier
        phoneNumberName.text = phoneNumber.toString()
    }
}