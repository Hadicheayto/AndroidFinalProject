package com.example.tpproject.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tpproject.PropertyViewModel
import com.example.tpproject.data.Property
import com.example.tpproject.R
import com.example.tpproject.RecycleViewAdapter
import com.example.tpproject.property_details
import com.example.tpproject.utilities.InjectorUtils

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private val propertyList = mutableListOf<Property>()
    private lateinit var viewModel: PropertyViewModel
    private var filteredPropertyList: List<Property> = propertyList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeUi()
    }

    private fun initializeUi() {
        val factory = InjectorUtils.providePropertiesViewModelFactory(requireContext())
        viewModel = ViewModelProvider(this, factory).get(PropertyViewModel::class.java)

        viewModel.properties.observe(viewLifecycleOwner, Observer { properties ->
            propertyList.clear()
            propertyList.addAll(properties)
            Log.e("msg:","msg:${properties}")
            updateRecyclerView()
        })


    }

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

        val searchBar = view.findViewById<EditText>(R.id.SearchBar)
        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed for this example
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterProperties(s.toString())
                val result = view.findViewById<TextView>(R.id.tvResult)
                result?.text = filteredPropertyList.size.toString() + " Result Found"
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
        intent.putExtra("supplier", "hadi")
        intent.putExtra("phoneNumber", "81640833")
        intent.putExtra("imgUrl", property.image)
        intent.putExtra("title", property.title)
        intent.putExtra("description", property.description)
        intent.putExtra("price", property.price)
        intent.putExtra("location", property.location)
        intent.putExtra("title", property.title)
        startActivity(intent)
    }
}
