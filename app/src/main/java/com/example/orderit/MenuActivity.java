package com.example.orderit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class MenuActivity extends AppCompatActivity {


    private FirebaseRecyclerOptions<Category> cat_options;
    private FirebaseRecyclerOptions<Food> food_options;
    private FirebaseRecyclerAdapter<Category, CategoryHolder> cat_adapter;
    private FirebaseRecyclerAdapter<Food, FoodHolder> food_adapter;
    private RecyclerView recyclerView;

    DatabaseReference categoryRef;
    DatabaseReference foodRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        recyclerView = findViewById(R.id.recycler_category);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        categoryRef = FirebaseDatabase.getInstance().getReference().child("category");

        loadCat();

    }


    private void loadCat() {
        cat_options = new FirebaseRecyclerOptions.Builder<Category>().setQuery(categoryRef, Category.class).build();
        cat_adapter = new FirebaseRecyclerAdapter<Category, CategoryHolder>(cat_options) {
            @Override
            protected void onBindViewHolder(@NonNull CategoryHolder holder, int position, @NonNull Category model) {

                holder.category_text.setText(model.getName());
                Picasso.get().load(model.getImage()).into(holder.category_image);
                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadMenu();
                    }
                });

            }

            @NonNull
            @Override
            public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_view_layout, parent, false);
                return new CategoryHolder(v);
            }
        };

        cat_adapter.startListening();
        recyclerView.setAdapter(cat_adapter);
    }

    private void loadMenu() {

        recyclerView=findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        foodRef = FirebaseDatabase.getInstance().getReference().child("food");



        food_options = new FirebaseRecyclerOptions.Builder<Food>().setQuery(foodRef,Food.class).build();
        food_adapter = new FirebaseRecyclerAdapter<Food, FoodHolder>(food_options) {
            @Override
            protected void onBindViewHolder(@NonNull FoodHolder holder, int position, @NonNull Food model) {

                holder.food_name.setText(""+model.getName());
                holder.price_name.setText(""+(double)(model).getPrice());

            }

            @NonNull
            @Override
            public FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_view_layout,parent,false);
                return new FoodHolder(v);
            }
        };

        food_adapter.startListening();
        recyclerView.setAdapter(food_adapter);
    }

}

