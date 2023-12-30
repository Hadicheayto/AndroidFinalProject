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
import com.example.tpproject.data.User
import com.example.tpproject.utilities.InjectorUtils
import com.google.android.material.textfield.TextInputLayout




class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val fname = findViewById<EditText>(R.id.fnameReg)
        val lname = findViewById<EditText>(R.id.lnameReg)
        val email = findViewById<EditText>(R.id.emailReg)
        val password = findViewById<EditText>(R.id.passwordReg)
        val btnRegister = findViewById<Button>(R.id.btnReg)
        val arrowBack = findViewById<ImageView>(R.id.ivArrowBackRegister)

        arrowBack.setOnClickListener{
            val intent = Intent(this, WelcomeScreenActivity::class.java)
            startActivity(intent)
        }

        val factory = InjectorUtils.providePropertiesViewModelFactory(this)
        val viewModel = ViewModelProvider(this, factory).get(PropertyViewModel::class.java)



        btnRegister.setOnClickListener{

                if(validateForm(fname,lname,email,password))
                {

                    val checkIfExist = viewModel.getUserByEmailAndPassword(email.text.toString(),password.text.toString())

                    if(checkIfExist != null)
                    {
                        Toast.makeText(this, "account with same email and password exist", Toast.LENGTH_SHORT).show()
                        email.setText("")
                        password.setText("")
                        return@setOnClickListener
                    }

                    val user = User(1,fname.text.toString(),lname.text.toString(),email.text.toString(),password.text.toString(),"empty",0)

                    val insertUser = viewModel.addUser(user)

                    if(insertUser > 0)
                {
                    Toast.makeText(this, "register Succesfully", Toast.LENGTH_SHORT).show()
                    fname.setText("")
                    lname.setText("")
                    email.setText("")
                    password.setText("")
                    val intent = Intent(this, SignInActivity::class.java)
                    startActivity(intent)
                }
                else
                {
                    Toast.makeText(this, "error registration", Toast.LENGTH_SHORT).show()
                }

            }
        }

    }

    private fun validateForm(
        fname: EditText?,
        lname: EditText?,
        email: EditText?,
        password: EditText?,
    ) : Boolean
    {
        var isValid =true;

        val fnameContainer = findViewById<TextInputLayout>(R.id.fnameContainer)
        if (fname?.text.toString().trim().isEmpty()) {
            fnameContainer.error = "fname is required"
            isValid = false
        } else {
            fnameContainer.error = null
        }

        val lnameContainer = findViewById<TextInputLayout>(R.id.lnameContainer)
        if (lname?.text.toString().trim().isEmpty()) {
            lnameContainer.error = "lname is required"
            isValid = false
        } else {
            lnameContainer.error = null
        }


        val emailContainer = findViewById<TextInputLayout>(R.id.emailContainer)
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

        val passwordContainer = findViewById<TextInputLayout>(R.id.passwordContainer)
        val passwordText = password?.text.toString().trim()
        if (passwordText.isEmpty()) {
            passwordContainer.error = "Password is required"
            isValid = false
        } else {
            // Password validation criteria
            val passwordRegex = "^(?=.*[A-Z])(?=.*[!@#\$%^&*(),.?\":{}|<>])(.{6,})$"
            if (!passwordText.matches(passwordRegex.toRegex())) {
                passwordContainer.error =
                    "Password must contain at least 1 uppercase letter, 1 symbol, and be at least 6 characters long"
                isValid = false
            } else {
                passwordContainer.error = null
            }
        }

        return isValid;
    }
}