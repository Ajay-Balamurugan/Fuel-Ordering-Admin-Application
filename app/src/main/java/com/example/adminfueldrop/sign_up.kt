package com.example.adminfueldrop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.adminfueldrop.databinding.ActivitySignUpBinding
import com.example.adminfueldrop.model.userModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import org.w3c.dom.Text

class sign_up : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database : DatabaseReference
    private lateinit var email : String
    private lateinit var password : String
    private lateinit var userName : String

    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setContentView(binding.root)
        //initialize firebase auth
        auth = Firebase.auth
        //initialize Firebase Database
        database = Firebase.database.reference


        binding.createbutton.setOnClickListener {
            userName = binding.ownername.text.toString()
            email = binding.email.text.toString()
            password = binding.password.text.toString()

            if(userName.isBlank() || email.isBlank() || password.isBlank()){
                Toast.makeText(this,"Please Fill All Fields",Toast.LENGTH_SHORT).show()
            }
            else{
                createaccount(userName,email,password)
            }

        }
        binding.alreadyhavebutton.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }
        val locationList = arrayOf("Chennai","Bengaluru","Mumbai","Delhi")
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,locationList)
        val autoCompleteTextView = binding.city
        autoCompleteTextView.setAdapter(adapter)
    }

    private fun createaccount(userName: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task->
            if(task.isSuccessful){
                Toast.makeText(this,"Account Created Successfully",Toast.LENGTH_SHORT).show()
                saveUserData()
                val intent = Intent(this, login::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this,"Account Creation Failed",Toast.LENGTH_SHORT).show()
            }
        }
    }

    //save data into database
    private fun saveUserData() {
        userName = binding.ownername.text.toString()
        email = binding.email.text.toString()
        password = binding.password.text.toString()
        val user = userModel(userName,email,password)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        database.child("user").child(userId).setValue(user)
    }
}