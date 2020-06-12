package com.example.orderit.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.orderit.models.Order
import org.jetbrains.annotations.TestOnly

@Dao
interface OrderDao {
    /* @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    User findByName(String first, String last);*/
    @Query("SELECT * FROM `Order`")
    fun getOrders(): LiveData<List<Order>>

    @TestOnly
    @Query("SELECT * FROM `Order`")
    fun getOrdersTest(): List<Order>

    @Query("DELETE FROM 'Order'")
    suspend fun deleteAllOrders()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg orders: Order)

    @Delete
    suspend fun delete(vararg order: Order)
}