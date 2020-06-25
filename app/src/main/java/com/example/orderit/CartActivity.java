package com.example.orderit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

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
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}