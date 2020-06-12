package com.example.orderit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.orderit.models.Food;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import static com.example.orderit.Helper.circularProgressDrawableOf;

public final class FoodAdapter extends FirebaseRecyclerAdapter<Food, FoodAdapter.FoodHolder> {

    private final Activity activity;

   // private final static String TAG = FoodAdapter.class.getSimpleName();
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     * @param activity
     */
    FoodAdapter(@NonNull FirebaseRecyclerOptions<Food> options, Activity activity) {
        super(options);
        this.activity = activity;
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void onBindViewHolder(@NonNull FoodHolder holder, int position, @NonNull Food model) {
        holder.food_name.setText(model.getName());
        Glide.with(holder.food_image.getContext())
                .load(model.getImage())
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(circularProgressDrawableOf(holder.food_image.getContext()))
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.food_image);


//        Picasso.get().load(model.getImage())
//                .placeholder(circularProgressDrawableOf(holder.food_image.getContext()))
//                .error(R.drawable.ic_launcher_foreground)
//                .fit()
//                .into(holder.food_image);
        holder.price_name.setText(String.format("Price : %.2f\u20ac" , model.getPrice()));
        holder.select_food.setOnClickListener(v -> {
            //GET SELECTED FOOD TO ANOTHER ACTIVITY
            Intent intent = new Intent(v.getContext() , SelectedFood.class);
            intent.putExtra("foodId", getRef(position).getKey());
            v.getContext().startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


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
