package com.example.orderit.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.orderit.models.Order;

import java.util.List;

@Dao
public interface OrderDao {
   /* @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    User findByName(String first, String last);*/
   @Query("SELECT * FROM `Order`")
    List<Order> getOrders();

    @Insert
    void insertAll(Order... orders);

    @Delete
    void delete(Order order);
}
