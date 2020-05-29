package com.example.orderit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public final class FoodAdapter extends FirebaseRecyclerAdapter<Food, FoodAdapter.FoodHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    FoodAdapter(@NonNull FirebaseRecyclerOptions<Food> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FoodHolder holder, int position, @NonNull Food model) {
        holder.food_name.setText(model.getName());
        holder.price_name.setText(String.format("%s", model.getPrice()));
    }

    @NonNull
    @Override
    public FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_view_layout, parent, false);
        return new FoodHolder(v);
    }

    static class FoodHolder extends RecyclerView.ViewHolder {

        ImageView food_image;
        TextView food_name;
        TextView price_name;

        FoodHolder(@NonNull View itemView) {
            super(itemView);
            food_image = itemView.findViewById(R.id.food_image);
            food_name = itemView.findViewById(R.id.food_name);
            price_name = itemView.findViewById(R.id.price_name);
        }
    }
}
