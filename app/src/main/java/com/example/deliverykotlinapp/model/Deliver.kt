package com.example.deliverykotlinapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "deliver_table")
data class Deliver(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val senderName: String,
    val senderContactNumber: Int,
    val pickUpPostcode: String,
    var pickUpDate: String,
    var pickUpTime: String,

    val recipientName: String,
    val recipientContactNumber: Int,
    val dropOffPostcode: String,
    val dropOffTime: String,

    val packageSize: String,
    val packageWeight: Int = 0,
    val quantity: Int = 0,

    val sum: Int = 0
) : Parcelable
