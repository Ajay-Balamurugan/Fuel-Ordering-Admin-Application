package com.example.adminfueldrop.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.adminfueldrop.databinding.DeliveryItemBinding

class DeliveryAdapter(
    private val customerNames:MutableList<String>,
    private val quantity:MutableList<String>,
    private val context: Context,
    private val itemClicked: onItemClicked
): RecyclerView.Adapter<DeliveryAdapter.DeliveryViewHolder>() {

interface onItemClicked{
    fun onItemClickListener(position: Int)
    fun onDispatchClickListener(position: Int)
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
        val binding = DeliveryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DeliveryViewHolder(binding)
    }


    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        holder.bind(position)
    }
    override fun getItemCount(): Int = customerNames.size

    inner class DeliveryViewHolder(private val binding: DeliveryItemBinding) :RecyclerView.ViewHolder(binding.root) {
        private var isAccepted = false
        fun bind(position: Int) {
            binding.apply {
                customername.text = customerNames[position]
                pendingOrderQuantity.text = quantity[position]
                acceptbutton.apply {
                    if(!isAccepted)
                    {
                        text = "Accept"
                    }
                    else
                    {
                        text = "Dispatch"
                    }
                    setOnClickListener {
                        if(!isAccepted){
                            text = "Dispatch"
                            isAccepted = true
                            showToast("Order is Accepted")
                        }
                        else{
                            customerNames.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            showToast("Order is Dispatched")
                            itemClicked.onDispatchClickListener(position)
                        }
                    }
                }
                itemView.setOnClickListener{
                    itemClicked.onItemClickListener(position)
                }
            }
        }
        private fun showToast(message: String){
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
        }
    }
}