package com.example.bastachat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bastachat.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //viewBinding initialization
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Firebase initialization
        fAuth = Firebase.auth

        //on-click functionalities
        binding.txtAlreadyHaveAnAccount.setOnClickListener { goToLoginActivity() }
        binding.txtLogin.setOnClickListener { goToLoginActivity() }
        binding.btnJoinNow.setOnClickListener { goToRegistrationActivity() }
    }
    private fun goToLoginActivity() {
        val myIntent = Intent(this, LoginActivity::class.java)
        startActivity(myIntent)
        finish()
    }
    private fun goToRegistrationActivity() {
        val myIntent = Intent(this, RegistrationActivity::class.java)
        startActivity(myIntent)
        finish()
    }
}