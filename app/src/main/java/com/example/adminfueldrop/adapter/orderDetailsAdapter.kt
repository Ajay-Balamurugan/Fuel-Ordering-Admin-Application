package com.example.adminfueldrop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminfueldrop.databinding.ActivityOrderDetailsBinding
import com.example.adminfueldrop.databinding.OrderDetailItemBinding
import com.example.adminfueldrop.model.orderDetails

class orderDetailsAdapter(private var context: Context,
                          private var fuelNames: ArrayList<String>,
                          private var fuelPrices: ArrayList<String>,
                          private var fuelQuantities: ArrayList<Int>,
    ):RecyclerView.Adapter<orderDetailsAdapter.OrderDetailsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailsViewHolder {
        val binding = OrderDetailItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return OrderDetailsViewHolder(binding)
    }


    override fun onBindViewHolder(holder: OrderDetailsViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = fuelNames.size

    inner class OrderDetailsViewHolder(private val binding: OrderDetailItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                fuelnametextview.text = fuelNames[position]
                fuelQuantityTextView.text = fuelQuantities[position].toString()
                pricetextview.text = fuelPrices[position]
            }

        }

    }
}