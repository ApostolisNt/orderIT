package com.example.orderit

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.orderit.models.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: OrderRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allOrders: LiveData<List<Order>>

    init {
        val orderDao = AppDatabase.getInstance(application).orderDao()
        repository = OrderRepository(orderDao)
        allOrders = repository.allOrders

    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insertOrders(vararg orders: Order) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(*orders)
    }

    fun updateOrder(order: Order) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(order)
    }

    fun deleteOrders(vararg orders: Order) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(*orders)
    }

    fun deleteAllOrders() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllOrders()
    }
}