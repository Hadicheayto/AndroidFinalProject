package com.example.tpproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.example.tpproject.utilities.InjectorUtils

class WelcomeScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_screen)

        val signin = findViewById<Button>(R.id.buttonSignIn)
        val register = findViewById<Button>(R.id.buttonRegister)
        var viewModel: PropertyViewModel

        signin.setOnClickListener{

//            val factory = InjectorUtils.providePropertiesViewModelFactory(this)
//            viewModel = ViewModelProvider(this, factory).get(PropertyViewModel::class.java)
//
//            viewModel.deleteAllProperties()


            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)


        }

        register.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }


    }
}