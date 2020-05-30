package com.example.orderit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class SelectedFood extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_food);
        final String foodId = getIntent().getStringExtra("foodId");
        final ImageView selected_food_img = findViewById(R.id.selected_food_img);
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("food");
          if (foodId != null) {
            ref.child(foodId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String image = dataSnapshot.child("image").getValue(String.class);
                        Glide.with(getApplicationContext())
                                .load(image)
                                .placeholder(Helper.circularProgressDrawableOf(getApplicationContext()))
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(selected_food_img);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
