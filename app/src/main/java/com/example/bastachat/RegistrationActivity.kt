package com.example.bastachat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.bastachat.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var fAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //viewbinding instantiation
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //firebase instantiation
        fAuth = Firebase.auth

        //on-click functionalities
        binding.btnSignUp.setOnClickListener { signUp() }
        binding.txtAlreadyHaveAnAccountRegistration.setOnClickListener { goToLoginActivity() }
        binding.txtLoginRegistration.setOnClickListener { goToLoginActivity() }
    }
    private fun goToLoginActivity() {
        val myIntent = Intent(this, LoginActivity::class.java)
        startActivity(myIntent)
        finish()
    }
    private fun signUp() {
        val emailRegistration = binding.edtEmailRegistration.text.toString()
        val passwordRegistration = binding.edtPasswordRegistration.text.toString()

        if (emailRegistration.isNullOrEmpty() || passwordRegistration.isNullOrEmpty()) {
            val toastText = "email and/or password is blank"
            val myToast = Toast.makeText(this@RegistrationActivity, toastText, 3)
            myToast.show()
            Log.d("LoginActivity","email and/or password is null or empty")
            return
        }

        fAuth.createUserWithEmailAndPassword(emailRegistration, passwordRegistration)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //code for jumping to home
                    val myIntent = Intent(this@RegistrationActivity, HomeActivity::class.java)
                    startActivity(myIntent)
                    finish()
                    //console logging
                    Log.d("RegistrationActivity", "Email is: $emailRegistration")
                    Log.d("RegistrationActivity", "Password is: $passwordRegistration")
                    Log.d("RegistrationActivity", "Successfully created user with uid: ${fAuth.currentUser!!.uid}")
                } else {
                    val toastText = "Incorrect Credential/s"
                    val myToast = Toast.makeText(this@RegistrationActivity, toastText, 3)
                    myToast.show()
                    Log.d("LoginActivity","Login failed")
                }
            }
    }
}