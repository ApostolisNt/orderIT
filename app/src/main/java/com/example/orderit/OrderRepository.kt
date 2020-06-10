package com.example.orderit

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.orderit.dao.OrderDao
import com.example.orderit.models.Order

class OrderRepository(private val orderDao: OrderDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allOrders: LiveData<List<Order>> = orderDao.getOrders()

    suspend fun insert(vararg orders: Order) {
        orderDao.insertAll(*orders)
        Log.d("repository", orders.toString())
    }

    suspend fun delete(vararg orders: Order) {
        orderDao.delete(*orders)
        Log.d("repository", orders.toString())
    }

    suspend fun deleteAllOrders() {
        orderDao.deleteAllOrders()
    }
}