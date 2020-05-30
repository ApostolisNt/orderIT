package com.example.orderit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderit.models.Food;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import static com.example.orderit.Helper.circularProgressDrawableOf;

public final class FoodAdapter extends FirebaseRecyclerAdapter<Food, FoodAdapter.FoodHolder> {

   // private final static String TAG = FoodAdapter.class.getSimpleName();
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
        Picasso.get().load(model.getImage())
                .placeholder(circularProgressDrawableOf(holder.food_image.getContext()))
                .fit()
                .into(holder.food_image);
        holder.price_name.setText(String.format("Price : %s", model.getPrice()));
        holder.select_food.setOnClickListener(v -> {
//            final Bundle bundle = new Bundle();
//            final String key = getRef(position).getKey();
//            Log.d(TAG, "key: " + key);
//            bundle.putString("id", key);
//            v.getContext().startActivity(new Intent(v.getContext(), SelectedFood.class), bundle);

            //GET SELECTED FOOD TO ANOTHER ACTIVITY
            Intent intent = new Intent(v.getContext() , SelectedFood.class);
            intent.putExtra("foodId", getRef(position).getKey());
            v.getContext().startActivity(intent);
        });

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
        Button select_food;

        FoodHolder(@NonNull View itemView) {
            super(itemView);
            food_image = itemView.findViewById(R.id.food_image);
            food_name = itemView.findViewById(R.id.food_name);
            price_name = itemView.findViewById(R.id.price_name);
            select_food = itemView.findViewById(R.id.select_food);
        }
    }
}
