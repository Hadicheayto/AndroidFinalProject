package com.example.tpproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.tpproject.data.UserManager
import com.example.tpproject.utilities.InjectorUtils
import com.google.android.material.textfield.TextInputLayout

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val email = findViewById<EditText>(R.id.emailSignin)
        val password = findViewById<EditText>(R.id.passwordSignin)
        val btnSignin = findViewById<Button>(R.id.btnSignin)
        val arrowBack = findViewById<ImageView>(R.id.ivArrowBackSignin)

        arrowBack.setOnClickListener{
            val intent = Intent(this, WelcomeScreenActivity::class.java)
            startActivity(intent)
        }



        val factory = InjectorUtils.providePropertiesViewModelFactory(this)
        val viewModel = ViewModelProvider(this, factory).get(PropertyViewModel::class.java)

        btnSignin.setOnClickListener{

            if(validateForm(email,password))
            {

                Log.e("SignInActivity", "Email: ${email.text}, Password: ${password.text}")

                val user = viewModel.getUserByEmailAndPassword(email.text.toString().lowercase(),password.text.toString())

                Log.e("SignInActivity", "user:: ${user}")

                if (user != null)
                {
                    UserManager.setUserId(user.id)
                    Toast.makeText(this, "sign Succesfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }else
                {
                    Toast.makeText(this, "wrong credentiels", Toast.LENGTH_SHORT).show()
                }
            }


        }


    }

    private fun validateForm(
        email: EditText?,
        password: EditText?,
    ) : Boolean
    {
        var isValid =true;

        val emailContainer = findViewById<TextInputLayout>(R.id.emailContainerSignin)
        if (email?.text.toString().trim().isEmpty()) {
            emailContainer.error = "email is required"
            isValid = false
        } else {
            emailContainer.error = null
        }

        // You can add more complex email validation logic if needed
        if (email != null) {
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.text).matches()) {
                emailContainer.error = "invalid email address"
                isValid = false
            }
        }

        val passwordContainer = findViewById<TextInputLayout>(R.id.passwordContainerSignin)
        if (password?.text.toString().trim().isEmpty()) {
            passwordContainer.error = "lname is required"
            isValid = false
        } else {
            passwordContainer.error = null
        }

        return isValid;
    }



}