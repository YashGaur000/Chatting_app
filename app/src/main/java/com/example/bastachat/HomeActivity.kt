package com.example.bastachat

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.bastachat.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var fAuth: FirebaseAuth

    private var selectedPhotoUri: Uri? = null
    private val getImage = registerForActivityResult(
        ActivityResultContracts.GetContent(),
        ActivityResultCallback {
            selectedPhotoUri = it
            binding.imageView.setImageURI(selectedPhotoUri)
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize viewBinding
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        fAuth = Firebase.auth

        binding.txtEmailHome.text = fAuth.currentUser?.email.toString()

        //on-click functionalities
        binding.btnLogOut.setOnClickListener {
            logOut()
            exitIfNotLoggedIn()
        }
        binding.btnUploadPhoto.setOnClickListener {
            Log.d("HomeActivity","try to show photo selector")

            openImageSelector()
            uploadImageToFirebaseStorage()
        }
    }
    override fun onStart() {
        super.onStart()
        exitIfNotLoggedIn()
    }
    private fun exitIfNotLoggedIn() {
        val currentUser = fAuth.currentUser
        if (currentUser == null) {
            val myIntent = Intent(this@HomeActivity, MainActivity::class.java)
            startActivity(myIntent)
            finish()
        }
    }
    private fun logOut() {
        FirebaseAuth.getInstance().signOut()
        Log.d("HomeActivity","Logging out")
    }
    private fun openImageSelector() {
        getImage.launch("image/*")
    }
    private fun uploadImageToFirebaseStorage() {
        if (selectedPhotoUri == null) { return }

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d("HomeActivity","Successfully uploaded image: ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                    it.toString()
                    Log.d("RegisterActivity", "File Location: $it")
                }
            }
    }
    private fun saveUserToFirebaseDatabase() {
    }

}