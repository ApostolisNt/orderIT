package com.example.orderit;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderit.models.Order;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemHolder> {

    private List<Order> list;
    private final OrderViewModel orderViewModel;
    final TextView total_price;

    public CartItemAdapter(OrderViewModel orderViewModel, TextView total_price) {
        this.orderViewModel = orderViewModel;
        this.total_price = total_price;
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
        final int quantity = order.getQuantity();
        holder.food_name.setText(order.getProductName());
        holder.quantity.setText(String.valueOf(quantity));
        holder.price_value.setText(String.format("Price : %.2f\u20ac", order.getPrice() * quantity));
        holder.plus_cart.setOnClickListener(v -> {
            order.setQuantity(order.getQuantity() + 1);
            holder.quantity.setText(String.valueOf(order.getQuantity()));
            holder.price_value.setText(String.format("Price : %.2f\u20ac", order.getPrice() * order.getQuantity()));
            // calculate total price
            calculateTotalPrice(order.getPrice());
            orderViewModel.updateOrder(order);
        });

        holder.minus_cart.setOnClickListener(v -> {
            if (order.getQuantity() == 1) return;
            order.setQuantity(order.getQuantity() - 1);
            holder.quantity.setText(String.valueOf(order.getQuantity()));
            holder.price_value.setText(String.format("Price : %.2f\u20ac",order.getPrice() * order.getQuantity()));
            // calculate total price
            calculateTotalPrice(-order.getPrice());
            orderViewModel.updateOrder(order);
        });


        holder.delete.setOnClickListener(v -> orderViewModel.deleteOrders(order));
    }

    @SuppressLint("DefaultLocale")
    private void calculateTotalPrice(double addedPrice) {
        // calculate total price
        final CharSequence priceText = total_price.getText();
        final String s = priceText.subSequence(0, priceText.length() - 1).toString().replace(',', '.');
        final double price = Double.parseDouble(s);
        final double totalPrice = price + addedPrice;
        total_price.setText(String.format("%.2f\u20ac", totalPrice));
    }

    @Override
    public int getItemCount() {
        if(list != null) return list.size();
        else
            return 0;
    }

    String getReceipt() {
        return list.stream().map(order -> {
            return String.format("%s: %s x %s", order.getProductName(), order.getPrice(), order.getQuantity());
        }).collect(Collectors.joining("\n"));
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
