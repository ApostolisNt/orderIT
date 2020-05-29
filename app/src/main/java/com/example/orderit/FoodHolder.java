package com.example.orderit;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class FoodHolder extends RecyclerView.ViewHolder {

    ImageView food_image;
    TextView food_name;
    TextView price_name;

    public FoodHolder(@NonNull View itemView) {
        super(itemView);

        food_image = itemView.findViewById(R.id.food_image);
        food_name = itemView.findViewById(R.id.food_name);
        price_name = itemView.findViewById(R.id.price_name);
    }
}