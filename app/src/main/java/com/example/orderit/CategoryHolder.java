package com.example.orderit;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class CategoryHolder extends RecyclerView.ViewHolder {

    ImageView category_image;
    TextView category_text;
    View v;

    public CategoryHolder(@NonNull View itemView) {
        super(itemView);

        category_image = itemView.findViewById(R.id.category_image);
        category_text = itemView.findViewById(R.id.category_name);
        v = itemView;
    }

}
