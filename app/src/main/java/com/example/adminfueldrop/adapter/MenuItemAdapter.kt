package com.example.adminfueldrop.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminfueldrop.databinding.ItemItemBinding
import com.example.adminfueldrop.model.AllMenu
import com.google.firebase.database.DatabaseReference


class MenuItemAdapter(
    private val context: Context,
    private val menuList: ArrayList<AllMenu>,
    databaseReference: DatabaseReference
): RecyclerView.Adapter<MenuItemAdapter.AddItemViewHolder>() {
    private val itemQuantities =IntArray(menuList.size){1}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        val binding = ItemItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AddItemViewHolder(binding)
    }


    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        holder.bind(position)
    }
    override fun getItemCount(): Int = menuList.size
    inner class AddItemViewHolder(private val binding: ItemItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]
                val menuItem = menuList[position]
                val uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/admin-fuel-drop.appspot.com/o/fuel.png?alt=media&token=ea4bc61f-5c79-4570-9c13-d12fcf9a067e")
                Glide.with(context).load(uri).into(fuelImageView)
                fuelnametextview.text= menuItem.fuelName
                pricetextview.text = menuItem.fuelPrice
                addtocartbutton.setOnClickListener {
                    addtocart()
                }

            }
        }

        private fun addtocart() {

        }
    }
}