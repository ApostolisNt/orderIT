package com.example.orderit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.spark.submitbutton.SubmitButton;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class CartActivity extends AppCompatActivity {

    @SuppressLint({"DefaultLocale", "ShowToast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        RecyclerView cartRecycler = findViewById(R.id.cartRecycler);
        final TextView total_price = findViewById(R.id.total_price);
        final Button paymentButton = findViewById(R.id.payment);
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



        paymentButton.setOnClickListener(view -> {
            try {
                OkhttpSingleton.run(cartItemAdapter.getReceipt());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}