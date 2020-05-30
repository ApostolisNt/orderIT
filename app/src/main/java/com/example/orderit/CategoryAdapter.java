package com.example.orderit;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.MergeAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.orderit.models.Category;
import com.example.orderit.models.Food;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.orderit.Helper.circularProgressDrawableOf;

public final class CategoryAdapter extends FirebaseRecyclerAdapter<Category, CategoryAdapter.CategoryHolder> {

    private FirebaseRecyclerOptions<Food> food_options;
    private DatabaseReference foodRef;
    private RecyclerView recycler_menu;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     * @param recycler_menu
     */
    public CategoryAdapter(@NonNull FirebaseRecyclerOptions<Category> options, RecyclerView recycler_menu) {
        super(options);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        foodRef = database.getReference().child("food");
        this.recycler_menu = recycler_menu;
    }



    @Override
    protected void onBindViewHolder(@NonNull CategoryHolder holder, int position, @NonNull Category model) {
        holder.category_text.setText(model.getName());
        GlideToVectorYou.init()
                .with(holder.category_image.getContext())
                .getRequestBuilder()
                .load(model.getImage())
                .placeholder(circularProgressDrawableOf(holder.category_image.getContext()))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.category_image);
        holder.v.setOnClickListener(v -> {
            food_options = new FirebaseRecyclerOptions.Builder<Food>().setQuery(
                    foodRef.orderByChild("menuID").equalTo(model.getCategoryID()), Food.class).build();
            RecyclerView.Adapter adapter = recycler_menu.getAdapter();
            if (adapter == null) {
                FoodAdapter foodAdapter = new FoodAdapter(food_options);
                foodAdapter.startListening();
                MergeAdapter mergeAdapter = new MergeAdapter(
                        getMockAdapter(holder.category_image.getContext()), foodAdapter);
                recycler_menu.setAdapter(mergeAdapter);
            } else {
                ((FoodAdapter)((MergeAdapter) adapter).getAdapters().get(1))
                        .updateOptions(food_options);
            }
        });

    }

    int dpToPixels(float dp, Context context) {
        // Converts 14 dip into its equivalent px
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                r.getDisplayMetrics()
        );
    }

    private RecyclerView.Adapter<RecyclerView.ViewHolder> getMockAdapter(Context context) {
        return new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = new View(context);
                view.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, dpToPixels(110, context)));
                return new RecyclerView.ViewHolder(view) {};
            }
            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) { }
            @Override
            public int getItemCount() { return 1; }
        };
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_view_layout, parent, false);
        return new CategoryHolder(v);
    }

    static class CategoryHolder extends RecyclerView.ViewHolder {

        ImageView category_image;
        TextView category_text;
        View v;

        CategoryHolder(@NonNull View itemView) {
            super(itemView);
            category_image = itemView.findViewById(R.id.category_image);
            category_text = itemView.findViewById(R.id.category_name);
            v = itemView;
        }

    }
}
