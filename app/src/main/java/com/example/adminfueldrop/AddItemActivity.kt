package com.example.adminfueldrop

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminfueldrop.adapter.MenuItemAdapter
import com.example.adminfueldrop.databinding.ActivityAddItemBinding
import com.example.adminfueldrop.model.AllMenu
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class AddItemActivity : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var fuelName: String
    private lateinit var fuelPrice: String
    private var fuelImageUri: Uri? = null
    private lateinit var auth: FirebaseAuth

    private val binding : ActivityAddItemBinding by lazy {
        ActivityAddItemBinding.inflate(layoutInflater)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        databaseReference = FirebaseDatabase.getInstance().reference

        //initialize firebase auth
        auth = FirebaseAuth.getInstance()
        //initialize Firebase Database
        database = FirebaseDatabase.getInstance()

        binding.additembutton.setOnClickListener {
            fuelName = binding.fuelname.text.toString()
            fuelPrice = binding.fuelprice.text.toString()
            fuelImageUri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/admin-fuel-drop.appspot.com/o/fuel.png?alt=media&token=ea4bc61f-5c79-4570-9c13-d12fcf9a067e")

            if(fuelName.isBlank() || fuelPrice.isBlank()){
                Toast.makeText(this,"Please Fill All Fields", Toast.LENGTH_SHORT).show()
            }
            else{
                uploadData()
                Toast.makeText(this,"Item Added Successfully", Toast.LENGTH_SHORT).show()
                finish()
            }

        }
    }

    private fun uploadData() {
        val MenuRef = database.getReference("menu")
        val newItemKey = MenuRef.push().key

        if(fuelImageUri != null)
        {
            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("menu_images/${newItemKey}.jpg")
            val newItem = AllMenu(
                fuelImage = fuelImageUri.toString(),
                fuelPrice = fuelPrice,
                fuelName = fuelName,
            )
            newItemKey?.let{
                key -> MenuRef.child(key).setValue(newItem).addOnSuccessListener {
                    Toast.makeText(this,"Item Added Successfully",Toast.LENGTH_SHORT).show()
            }
                .addOnFailureListener {
                    Toast.makeText(this,"Failed to Add Item",Toast.LENGTH_SHORT).show()
                }
            }
        }

    }


}