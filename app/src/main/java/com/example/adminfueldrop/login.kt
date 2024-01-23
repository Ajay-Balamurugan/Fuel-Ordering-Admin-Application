package com.example.adminfueldrop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.adminfueldrop.databinding.ActivityLoginBinding
import com.example.adminfueldrop.databinding.ActivitySignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database : DatabaseReference
    private lateinit var email : String
    private lateinit var password : String

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //initialize firebase auth
        auth = Firebase.auth
        //initialize Firebase Database
        database = Firebase.database.reference

        binding.loginbutton.setOnClickListener {
            //get text from edit text
            email = binding.email.text.toString()
            password = binding.password.text.toString()

            if(email.isBlank() || password.isBlank()){
                Toast.makeText(this,"Please Fill All Fields", Toast.LENGTH_SHORT).show()
            }
            else{
                signInAccount(email,password)
            }

        }
        binding.donthavebutton.setOnClickListener {
            val intent = Intent(this, sign_up::class.java)
            startActivity(intent)
        }
    }

    private fun signInAccount(email: String, password: String) {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(this,"Successfully Logged in",Toast.LENGTH_SHORT).show()
                val user = auth.currentUser
                updateui(user)
            }
            else
            {
                Toast.makeText(this,"Wrong Email/Password",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateui(user: FirebaseUser?) {
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}