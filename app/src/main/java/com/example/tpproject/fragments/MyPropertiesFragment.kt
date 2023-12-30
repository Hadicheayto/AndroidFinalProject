package com.example.tpproject.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tpproject.PropertyViewModel
import com.example.tpproject.R
import com.example.tpproject.RecycleViewAdapter
import com.example.tpproject.data.Property
import com.example.tpproject.data.UserManager
import com.example.tpproject.utilities.InjectorUtils


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MyPropertiesFragment : Fragment() {


    private lateinit var recycleViewProfile: RecyclerView
    private lateinit var viewModel: PropertyViewModel
    private val propertyList = mutableListOf<Property>()
    private  lateinit var noteEmpty: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_my_properties, container, false)

        noteEmpty = rootView?.findViewById<TextView>(R.id.tvNoteEmpty)!!

        recycleViewProfile = rootView.findViewById(R.id.RvMyProperties)
        recycleViewProfile.setBackgroundColor(Color.TRANSPARENT)
        recycleViewProfile.layoutManager = LinearLayoutManager(requireContext())

        val factory = InjectorUtils.providePropertiesViewModelFactory(requireContext())
        viewModel = ViewModelProvider(this, factory).get(PropertyViewModel::class.java)




        val propertyArray = viewModel.getPropertiesByUserId(UserManager.getUserId())
        propertyList.clear()
        propertyList.addAll(propertyArray)
        propertyList.reverse()

        recycleViewProfile.adapter = RecycleViewAdapter(propertyList,true) { selectedItem: Property ->
            listItemClicked(selectedItem)
        }

        Log.e("msg:","size= ${propertyList.size}")
        if(propertyList.size == 0)
        {
            noteEmpty?.text = " You don't have any property"
        }

        return rootView
    }

    private fun listItemClicked(selectedItem: Property) {
        viewModel.deletePropertyById(selectedItem.id)
        propertyList.remove(selectedItem)
        updateRecyclerView()
        if(propertyList.size == 0)
        {
            noteEmpty?.text = " You don't have any property"
        }
    }


    private fun updateRecyclerView() {
        recycleViewProfile.adapter = RecycleViewAdapter(propertyList, true) { selectedItem: Property ->
            listItemClicked(selectedItem)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyPropertiesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
