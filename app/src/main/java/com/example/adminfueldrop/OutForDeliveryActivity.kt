package com.example.adminfueldrop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminfueldrop.adapter.DeliveryAdapter
import com.example.adminfueldrop.databinding.ActivityAddItemBinding
import com.example.adminfueldrop.databinding.ActivityOutForDeliveryBinding
import com.example.adminfueldrop.model.orderDetails
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.FieldPosition

class OutForDeliveryActivity : AppCompatActivity(), DeliveryAdapter.onItemClicked{
    private val binding : ActivityOutForDeliveryBinding by lazy {
        ActivityOutForDeliveryBinding.inflate(layoutInflater)
    }
    private var listOfName: MutableList<String> = mutableListOf()
    private var listOfTotalPrice: MutableList<String> = mutableListOf()
    private var listOfOrderItem: ArrayList<orderDetails> = arrayListOf()
    private lateinit var database:FirebaseDatabase
    private lateinit var databaseOrderDetails: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //initialize databse
        database = FirebaseDatabase.getInstance()
        databaseOrderDetails = database.reference.child("OrderDetails")

        getOrderDetails()



        //binding.deliveryRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun getOrderDetails() {
        //retrieve order details from firebase
        databaseOrderDetails.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(orderSnapshot in snapshot.children){
                    val orderDetails = orderSnapshot.getValue(orderDetails::class.java)
                    orderDetails?.  let {
                        listOfOrderItem.add(it)
                    }
                }
                addDataToListForRecyclerView()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun addDataToListForRecyclerView() {
        for(orderItem in listOfOrderItem){
            //add to respective list
            orderItem.userName?.let { listOfName.add(it)}
            orderItem.totalPrice?.let { listOfTotalPrice.add(it)}
        }
        setAdapter()
    }

    private fun setAdapter() {
        binding.deliveryRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = DeliveryAdapter(listOfName,listOfTotalPrice,this,this)
        binding.deliveryRecyclerView.adapter = adapter
    }

    override fun onItemClickListener(position: Int) {
        val intent = Intent(this,OrderDetailsActivity::class.java)
        val userOrderDetails = listOfOrderItem[position]
        intent.putExtra("userOrderDetails",userOrderDetails)
        startActivity(intent)

    }

    override fun onDispatchClickListener(position: Int) {
        //remove the order from database once the order is dispatched
        val dispatchItemPushKey = listOfOrderItem[position].itemPushKey
        deleteThisOrderFromOrderDetails(dispatchItemPushKey!!)
    }


    private fun deleteThisOrderFromOrderDetails(dispatchItemPushKey: String) {
        val orderDetailsItemReference = database.reference.child("OrderDetails").child(dispatchItemPushKey)
        orderDetailsItemReference.removeValue()
    }


}