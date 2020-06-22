package com.example.orderit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.orderit.models.Order;

public class CartActivity extends AppCompatActivity {

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        RecyclerView cartRecycler = findViewById(R.id.cartRecycler);
        final TextView total_price = findViewById(R.id.total_price);
        final OrderViewModel orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        final CartItemAdapter cartItemAdapter = new CartItemAdapter(orderViewModel, total_price);
        orderViewModel.getAllOrders().observe(this, list -> {
            cartItemAdapter.setOrders(list);
            final double sum = list.parallelStream()
                    .mapToDouble(order -> order.getPrice() * order.getQuantity())
                    .sum();
            total_price.setText(String.format("%.2f\u20ac", sum));
        });
        cartRecycler.setAdapter(cartItemAdapter);

        // set Table name
        final String tableName = getSharedPreferences("shared", Context.MODE_PRIVATE).getString("tableName", "0");
        final TextView tableNameText = findViewById(R.id.table_name);
        tableNameText.setText(String.format("Table : %s", tableName));
    }
}