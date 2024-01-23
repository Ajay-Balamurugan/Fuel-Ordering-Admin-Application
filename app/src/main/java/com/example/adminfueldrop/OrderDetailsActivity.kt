package com.example.adminfueldrop

import android.location.Address
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminfueldrop.adapter.orderDetailsAdapter
import com.example.adminfueldrop.databinding.ActivityOrderDetailsBinding
import com.example.adminfueldrop.model.orderDetails

class OrderDetailsActivity : AppCompatActivity() {
    private val binding : ActivityOrderDetailsBinding by lazy {
        ActivityOrderDetailsBinding.inflate(layoutInflater)
    }
    private var userName :String? = null
    private var address :String? = null
    private var phoneNumber :String? = null
    private var totalPrice:String? = null
    private var fuelNames :ArrayList<String> = arrayListOf()
    private var fuelPrices :ArrayList<String> = arrayListOf()
    private var fuelQuantities :ArrayList<Int> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getDataFromIntent()
    }

    private fun getDataFromIntent() {
        val recievedOrderDetails = intent.getSerializableExtra("userOrderDetails") as orderDetails
        recievedOrderDetails?.let{orderDetails ->
                userName = recievedOrderDetails.userName
                fuelNames = recievedOrderDetails.fuelNames as ArrayList<String>
                fuelQuantities = recievedOrderDetails.fuelQuantities as ArrayList<Int>
                address = recievedOrderDetails.address
                phoneNumber = recievedOrderDetails.phoneNumber
                fuelPrices = recievedOrderDetails.fuelPrices as ArrayList<String>
                totalPrice = recievedOrderDetails.totalPrice

                setUserDetails()
                setAdapter()
        }

    }

    private fun setUserDetails() {
        binding.name.text = userName
        binding.address.text = address
        binding.phone.text = phoneNumber
    }

    private fun setAdapter() {
    binding.orderDetailsRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = orderDetailsAdapter(this,fuelNames,fuelPrices,fuelQuantities)
        binding.orderDetailsRecyclerView.adapter = adapter
    }
}