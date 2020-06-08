package com.example.orderit;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.orderit.dao.OrderDao;
import com.example.orderit.models.Order;

import java.util.concurrent.Executors;

@Database(entities = {Order.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract OrderDao orderDao();
    private static AppDatabase instance = null;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context,
                    AppDatabase.class, "OrderItDb")
                    .setTransactionExecutor(Executors.newSingleThreadExecutor())
                    .build();
        }
        return instance;
    }
}