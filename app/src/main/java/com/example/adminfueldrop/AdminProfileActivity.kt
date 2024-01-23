package com.example.adminfueldrop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.adminfueldrop.databinding.ActivityAdminProfileBinding
import com.example.adminfueldrop.databinding.ActivityMainBinding
import com.example.adminfueldrop.model.userModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdminProfileActivity : AppCompatActivity() {
    private val binding : ActivityAdminProfileBinding by lazy {
        ActivityAdminProfileBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var adminReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        adminReference = database.reference.child("user")

        binding.saveinfobutton.setOnClickListener {
            updateUserData()
        }

        retrieveUserData()
    }


    private fun retrieveUserData() {
        val currentUserUid = auth.currentUser?.uid
        if(currentUserUid != null){
            val userReference = adminReference.child(currentUserUid)

            userReference.addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        var ownerName = snapshot.child("ownername").getValue()
                        var email = snapshot.child("email").getValue()
                        var password = snapshot.child("password").getValue()
                        setDataToTextView(ownerName,email,password)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        }

    }

    private fun setDataToTextView(ownerName: Any?, email: Any?, password: Any?) {
        binding.adminname.setText(ownerName.toString())
        binding.adminemail.setText(email.toString())
        binding.adminpassword.setText(password.toString())

    }
    private fun updateUserData() {
        var updateName = binding.adminname.text.toString()
        var updateEmail = binding.adminemail.text.toString()
        var updatePassword = binding.adminpassword.text.toString()
        var updatePhone = binding.adminphone.text.toString()
        var currentUserId = auth.currentUser?.uid
        if(currentUserId != null){
            val userReference = adminReference.child(currentUserId)
            userReference.child("ownername").setValue(updateName)
            userReference.child("email").setValue(updateEmail)
            userReference.child("password").setValue(updatePassword)
            userReference.child("phone").setValue(updatePhone)
            Toast.makeText(this,"Profile Updated Successfully",Toast.LENGTH_SHORT).show()
            auth.currentUser?.updateEmail(updateEmail)
            auth.currentUser?.updatePassword(updatePassword)
        }
        else{
            Toast.makeText(this,"Failed to Update Profile",Toast.LENGTH_SHORT).show()
        }
    }
}