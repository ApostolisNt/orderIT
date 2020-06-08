package com.example.orderit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.orderit.models.Order;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView cartRecycler;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartRecycler = findViewById(R.id.cartRecycler);
        appDatabase = AppDatabase.getInstance(this);

        final List<Order> orderList = appDatabase.orderDao().getOrders();
        final CartItemAdapter cartItemAdapter = new CartItemAdapter(orderList);

        cartRecycler.setAdapter(cartItemAdapter);

    }
}