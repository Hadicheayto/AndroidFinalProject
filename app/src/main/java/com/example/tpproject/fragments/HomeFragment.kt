package com.example.tpproject.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tpproject.Property
import com.example.tpproject.R
import com.example.tpproject.RecycleViewAdapter
import com.example.tpproject.property_details

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null


    private val propertyList = listOf(
        Property("hadi cheayto", 81640833, "villa", "villa sur mer sur la poiramide et voila", "rawche", 300000, R.drawable.property1),
        Property("hadi cheayto", 81640833, "house", "big house", "rawche", 18000, R.drawable.property2),
        Property("hadi cheayto", 81640833, "apartment", "large apartment", "rawche", 15000, R.drawable.property3),
        Property("hadi cheayto", 81640833, "cabana", "cabana sur mer", "rawche", 90000, R.drawable.property2),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private var filteredPropertyList: List<Property> = propertyList

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)


        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.setBackgroundColor(Color.TRANSPARENT)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = RecycleViewAdapter(propertyList) { selectedItem: Property ->
            listItemClicked(selectedItem)
        }


        val result = view.findViewById<TextView>(R.id.tvResult)
        result.text = propertyList.size.toString() + " Result Found"

        val searchBar = view.findViewById<EditText>(R.id.SearchBar);

        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed for this example
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterProperties(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {
                // Not needed for this example
            }
        })


        return view
    }

    private fun filterProperties(query: String) {
        filteredPropertyList = if (query.isEmpty()) {
            propertyList
        } else {
            propertyList.filter { property ->
                property.title.contains(query, ignoreCase = true)
            }
        }

        updateRecyclerView()
    }

    private fun updateRecyclerView() {
        val recyclerView = requireView().findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = RecycleViewAdapter(filteredPropertyList) { selectedItem: Property ->
            listItemClicked(selectedItem)
        }
    }

    private fun listItemClicked(property: Property) {
        val intent = Intent(requireContext(), property_details::class.java)
        intent.putExtra("supplier", property.supplier)
        intent.putExtra("phoneNumber", property.phoneNumber)
        intent.putExtra("imgUrl", property.image)
        intent.putExtra("title", property.title)
        intent.putExtra("description", property.description)
        intent.putExtra("price", property.price)
        intent.putExtra("location", property.location)
        intent.putExtra("title", property.title)
        startActivity(intent)
    }
}
