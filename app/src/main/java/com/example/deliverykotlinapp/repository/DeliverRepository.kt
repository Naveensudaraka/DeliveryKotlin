package com.example.deliverykotlinapp.repository

import androidx.lifecycle.LiveData
import com.example.deliverykotlinapp.data.DeliverDao
import com.example.deliverykotlinapp.model.Deliver

// User Repository abstracts access to multiple data sources. However this is not the part of the Architecture Component libraries.

class DeliverRepository(private val deliverDao: DeliverDao) {
    val readAllData: LiveData<List<Deliver>> = deliverDao.readAllData()

    suspend fun addDeliver(deliver: Deliver) {
        deliverDao.addDeliver(deliver)
    }

    suspend fun updateDeliver(deliver: Deliver) {
        deliverDao.updateDeliver(deliver)
    }

    suspend fun deleteDeliver(deliver: Deliver) {
        deliverDao.deleteDeliver(deliver)
    }

    suspend fun deleteAllDelivers() {
        deliverDao.deleteAllDelivers()
    }
}