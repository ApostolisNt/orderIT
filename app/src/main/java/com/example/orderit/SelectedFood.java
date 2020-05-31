package com.example.orderit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SelectedFood extends AppCompatActivity {

    private ImageView selected_food_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_food);
//        calcImageSize();
        final CardView select_food_cv = findViewById(R.id.select_food_cv);
        final String foodId = getIntent().getStringExtra("foodId");
        final TextView food_name = findViewById(R.id.food_name);
        final TextView description = findViewById(R.id.description);
        selected_food_img = findViewById(R.id.selected_food_img);
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("food");
          if (foodId != null) {
            ref.child(foodId).addValueEventListener(new ValueEventListener() {
                private boolean isImageMeasured = false;
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String image = dataSnapshot.child("image").getValue(String.class);
                        String name = dataSnapshot.child("name").getValue(String.class);
                        String descriptionStr = dataSnapshot.child("description").getValue(String.class);

                        food_name.setText(name);
                        description.setText(descriptionStr);
                        // Calculates dynamic height to equal width
                        if (!isImageMeasured) {
                            select_food_cv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                @Override
                                public void onGlobalLayout() {
                                    isImageMeasured = true;
                                    final int measuredWidth = select_food_cv.getWidth();
                                    //noinspection SuspiciousNameCombination
                                    select_food_cv.getLayoutParams().height = measuredWidth;
                                    //  setRadius to Half width for circle
                                    select_food_cv.setRadius((float) measuredWidth / 2F);

                                    setImage(image);
                                    select_food_cv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                }
                            });
                        } else {
                            setImage(image);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void setImage(String url) {
        Glide.with(getApplicationContext())
            .load(url)
            .placeholder(Helper.circularProgressDrawableOf(getApplicationContext()))
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(R.drawable.ic_launcher_foreground)
            .into(selected_food_img);
    }
}

