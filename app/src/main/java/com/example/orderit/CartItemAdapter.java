package com.example.orderit;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderit.models.Order;

import java.util.List;

class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemHolder> {

    private List<Order> list;
    private final OrderViewModel orderViewModel;

    public CartItemAdapter(OrderViewModel orderViewModel) {
        this.orderViewModel = orderViewModel;
    }

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
//        Log.d("adapter", order.getProductID());
        holder.food_name.setText(order.getProductName());
        holder.quantity.setText(String.valueOf(order.getQuantity()));
        final int quantity = Integer.parseInt((holder.quantity.getText().toString()));
        holder.price_value.setText(String.format("%.2f\u20ac", order.getPrice() * (quantity)));
        holder.plus_cart.setOnClickListener(v -> {
            holder.quantity.setText(String.valueOf(Integer.parseInt(holder.quantity.getText().toString()) + 1));
            final int quantity_plus = Integer.parseInt((holder.quantity.getText().toString()));
            holder.price_value.setText(String.format("%.2f\u20ac", order.getPrice() * (quantity_plus)));
        });

        holder.minus_cart.setOnClickListener(v -> {
            final int quantity_minus = Integer.parseInt(holder.quantity.getText().toString());
            if (quantity_minus == 1) return;
            final int newQuantityValue = quantity_minus - 1;
            holder.quantity.setText(String.format("%d", newQuantityValue));
            final double totalPrice = newQuantityValue * order.getPrice();
            holder.price_value.setText(String.format("%.2f\u20ac", totalPrice));
        });


        holder.delete.setOnClickListener(v -> orderViewModel.delete(order));
    }

    @Override
    public int getItemCount() {
        if(list != null) return list.size();
        else
            return 0;
    }

    public static class CartItemHolder extends RecyclerView.ViewHolder {

        TextView food_name;
        TextView price_value;
        TextView quantity;
        ImageView delete;
        ImageView plus_cart;
        ImageView minus_cart;

        public CartItemHolder(@NonNull View itemView) {
            super(itemView);
            food_name = itemView.findViewById(R.id.food_name);
            price_value = itemView.findViewById(R.id.price_name);
            quantity = itemView.findViewById(R.id.quantity);
            delete = itemView.findViewById(R.id.delete);
            plus_cart = itemView.findViewById(R.id.plus_cart);
            minus_cart = itemView.findViewById(R.id.minus_cart);
        }
    }
}
