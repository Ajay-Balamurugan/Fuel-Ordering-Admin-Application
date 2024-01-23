package com.example.adminfueldrop.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class orderDetails(): Serializable {
    var userUid: String? = null
    var userName: String? = null
    var fuelNames: MutableList<String>? = null
    var fuelImages: MutableList<String>? = null
    var fuelPrices: MutableList<String>? = null
    var fuelQuantities: MutableList<Int>? = null
    var address: String? = null
    var totalPrice: String? = null
    var phoneNumber: String? = null
    var itemPushKey: String? = null
    var currentTime: Long = 0

    constructor(parcel: Parcel) : this() {
        userUid = parcel.readString()
        userName = parcel.readString()
        address = parcel.readString()
        totalPrice = parcel.readString()
        phoneNumber = parcel.readString()
        itemPushKey = parcel.readString()
        currentTime = parcel.readLong()
    }

    fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    fun writeToParcel(dest: Parcel, flags: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<orderDetails> {
        override fun createFromParcel(parcel: Parcel): orderDetails {
            return orderDetails(parcel)
        }

        override fun newArray(size: Int): Array<orderDetails?> {
            return arrayOfNulls(size)
        }
    }
}