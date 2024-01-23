package com.example.adminfueldrop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adminfueldrop.databinding.ActivityAddItemBinding
import com.example.adminfueldrop.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var completedOrderReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.additemcard.setOnClickListener {
            val intent = Intent(this,AddItemActivity::class.java)
            startActivity(intent)
        }

        binding.adminprofilecard.setOnClickListener {
            val intent = Intent(this,AdminProfileActivity::class.java)
            startActivity(intent)
        }
        binding.outfordeliverycard.setOnClickListener {
            val intent = Intent(this,OutForDeliveryActivity::class.java)
            startActivity(intent)
        }
        binding.logoutcard.setOnClickListener {
            val intent = Intent(this,login::class.java)
            startActivity(intent)
        }

        pendingOrders()
        earning()
    }

    private fun earning() {
        database = FirebaseDatabase.getInstance()
        var pendingOrderReference = database.reference.child("OrderDetails")
        var earning = 0
        pendingOrderReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (orderSnapshot in snapshot.children) {
                    var orderTotalPrice = orderSnapshot.child("totalPrice").getValue(String::class.java)
                    if (orderTotalPrice != null) {
                        orderTotalPrice = orderTotalPrice.drop(2)
                    }
                    var price = orderTotalPrice!!.toInt()
                    if (orderTotalPrice != null) {
                        earning += price
                    }
                }
                binding.earnings.text = "₹ " + earning.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun pendingOrders() {
        database = FirebaseDatabase.getInstance()
        var pendingOrderReference = database.reference.child("OrderDetails")
        var pendingOrderItemCount = 0
        pendingOrderReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                pendingOrderItemCount = snapshot.childrenCount.toInt()
                binding.pendingorders.text = pendingOrderItemCount.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}