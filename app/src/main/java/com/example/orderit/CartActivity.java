package com.example.orderit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.orderit.models.Order;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        RecyclerView cartRecycler = findViewById(R.id.cartRecycler);
        final OrderViewModel orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        final CartItemAdapter cartItemAdapter = new CartItemAdapter();
        orderViewModel.getAllOrders().observe(this, cartItemAdapter::setOrders);
        cartRecycler.setAdapter(cartItemAdapter);
    }
}