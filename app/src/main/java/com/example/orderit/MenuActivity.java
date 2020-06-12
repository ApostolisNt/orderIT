package com.example.orderit;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.selection.SelectionPredicates;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderit.models.Category;
import com.example.orderit.models.Food;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MenuActivity extends AppCompatActivity {


    private FirebaseRecyclerOptions<Category> cat_options;
    private FirebaseRecyclerOptions<Food> food_options;
    private RecyclerView recycler_category;
    private RecyclerView recycler_menu;

    DatabaseReference categoryRef;
    DatabaseReference foodRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef;
        myRef = database.getReference();

        recycler_category = findViewById(R.id.recycler_category);
        recycler_menu = findViewById(R.id.recycler_menu);

        categoryRef = myRef.child("category");
        foodRef = database.getReference().child("food");

        loadCat();

    }


    private void loadCat() {
        cat_options = new FirebaseRecyclerOptions.Builder<Category>().setQuery(categoryRef, Category.class).build();
        final CategoryAdapter cat_adapter = new CategoryAdapter(cat_options, recycler_menu, this);
        cat_adapter.startListening();
        recycler_category.setAdapter(cat_adapter);

        final SelectionTracker<String> tracker = new SelectionTracker.Builder<>(
                "mySelection",
                recycler_category,
                new MyItemKeyProvider(cat_adapter),
                new MyItemDetailsLookup(recycler_category),
                StorageStrategy.createStringStorage()
        ).withSelectionPredicate(
                SelectionPredicates.createSelectSingleAnything()
        ).build();
        cat_adapter.tracker = tracker;
    }

}

