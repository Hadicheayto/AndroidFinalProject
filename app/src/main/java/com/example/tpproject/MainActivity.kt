package com.example.tpproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.tpproject.fragments.ChatFragment
import com.example.tpproject.fragments.HomeFragment
import com.example.tpproject.fragments.CreatePropertyFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        val homeFragment = HomeFragment()
        val propertFragment = CreatePropertyFragment()
        val chatFragment = ChatFragment()

        makeCurrentFragment(homeFragment)


        bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.ic_home -> makeCurrentFragment(homeFragment)
                R.id.ic_add -> makeCurrentFragment(propertFragment)
                R.id.ic_chat -> makeCurrentFragment(chatFragment)
            }
            true
        }

    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper,fragment)
            commit()
        }



//    val propertyList = listOf<Property>(
//        Property("hadi cheayto",81640833,"villa","villa sur mer","rawche",300000,R.drawable.property1),
//        Property("hadi cheayto",81640833,"house","big house","rawche",18000,R.drawable.property2),
//        Property("hadi cheayto",81640833,"apartment","large apartment","rawche",15000,R.drawable.property3),
//        Property("hadi cheayto",81640833,"cabana","cabana sur mer","rawche",90000,R.drawable.property2),
//    )
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        val recycleView = findViewById<RecyclerView>(R.id.recyclerView)
//        recycleView.setBackgroundColor(Color.TRANSPARENT)
//        recycleView.layoutManager = LinearLayoutManager(this)
//        recycleView.adapter = RecycleViewAdapter(propertyList ) { selectedItem: Property ->
//            listItemClicked(selectedItem)
//        }
//        val result = findViewById<TextView>(R.id.tvResult)
//        result.text = propertyList.size.toString() + " Result Found"
//    }
//
//    private fun listItemClicked(property: Property){
//        val intent = Intent(this,property_details::class.java)
//        intent.putExtra("supplier",property.supplier)
//        intent.putExtra("phoneNumber",property.phoneNumber)
//        intent.putExtra("imgUrl",property.image)
//        intent.putExtra("title",property.title);
//        intent.putExtra("description",property.description);
//        intent.putExtra("price",property.price);
//        intent.putExtra("location",property.location);
//        intent.putExtra("title",property.title);
//        startActivity(intent)
//    }
}