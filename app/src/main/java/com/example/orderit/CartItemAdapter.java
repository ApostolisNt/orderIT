package com.example.orderit;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderit.models.Category;
import com.example.orderit.models.Order;

import java.util.ArrayList;
import java.util.List;

class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemHolder> {

    private List<Order> list;

    public void setOrders(List<Order> list){
       this.list = list;
       notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_view_layout, parent, false);
        return new CartItemAdapter.CartItemHolder(v);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull CartItemHolder holder, int position) {
        final Order order = list.get(position);
        Log.d("adapter", order.getProductID());
        holder.food_name.setText(order.getProductName());
        holder.price_name.setText(String.format("%f", order.getPrice()));
        holder.quantity.setText(String.valueOf(order.getQuantity()));
    }

    @Override
    public int getItemCount() {
        if(list != null) return list.size();
        else
            return 0;
    }

    public static class CartItemHolder extends RecyclerView.ViewHolder {

        TextView food_name;
        TextView price_name;
        TextView quantity;

        public CartItemHolder(@NonNull View itemView) {
            super(itemView);
            food_name = itemView.findViewById(R.id.food_name);
            price_name = itemView.findViewById(R.id.price_name);
            quantity = itemView.findViewById(R.id.quantity);
        }
    }
}
