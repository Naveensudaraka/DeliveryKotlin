package com.example.deliverykotlinapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.deliverykotlinapp.data.DeliverDatabase
import com.example.deliverykotlinapp.model.Deliver
import com.example.deliverykotlinapp.repository.DeliverRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

    // DeliverViewModel provides delivers data to the UI and survive configuration changes.
// A ViewModel acts as a communication center between the Repository and the UI.

    class DeliverViewModel(application: Application): AndroidViewModel(application) {
        val readAllData: LiveData<List<Deliver>>
        private val repository: DeliverRepository

        init {
            val deliverDao = DeliverDatabase.getDatabase(application).deliverDao()
            repository= DeliverRepository(deliverDao)
            readAllData = repository.readAllData
        }

        fun addDeliver(deliver: Deliver) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.addDeliver(deliver)
            }
        }

        fun updateDeliver(deliver: Deliver) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.updateDeliver(deliver)
            }
        }

        fun deleteDeliver(deliver: Deliver) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.deleteDeliver(deliver)
            }
        }

        fun deleteAllDelivers() {
            viewModelScope.launch(Dispatchers.IO) {
                repository.deleteAllDelivers()
            }
        }
    }
