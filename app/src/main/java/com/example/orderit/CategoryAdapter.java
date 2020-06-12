package com.example.orderit;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.SelectionTracker;
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

import org.jetbrains.annotations.NotNull;

import static com.example.orderit.Helper.circularProgressDrawableOf;

public final class CategoryAdapter extends FirebaseRecyclerAdapter<Category, CategoryAdapter.CategoryHolder> {

    private FirebaseRecyclerOptions<Food> food_options;
    private DatabaseReference foodRef;
    private RecyclerView recycler_menu;
    private final Activity activity;
    public SelectionTracker<String> tracker = null;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *  @param options
     * @param recycler_menu
     * @param activity
     */
    public CategoryAdapter(@NonNull FirebaseRecyclerOptions<Category> options, RecyclerView recycler_menu, Activity activity) {
        super(options);
        this.activity = activity;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        foodRef = database.getReference().child("food");
        this.recycler_menu = recycler_menu;
        this.setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return position;
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
        /*holder.v.setOnClickListener(v -> {
                });*/
        // onSelect
        if (tracker != null) {
            final boolean selected = tracker.isSelected(model.getCategoryID());
            if (selected) {
                holder.category_image.animate()
                        .rotationBy(360F)
                        .start();
                food_options = new FirebaseRecyclerOptions.Builder<Food>().setQuery(
                        foodRef.orderByChild("menuID").equalTo(model.getCategoryID()), Food.class).build();
                RecyclerView.Adapter adapter = recycler_menu.getAdapter();
                if (adapter == null) {
                    FoodAdapter foodAdapter = new FoodAdapter(food_options, activity);
                    foodAdapter.startListening();
                    MergeAdapter mergeAdapter = new MergeAdapter(
                            getMockAdapter(holder.category_image.getContext()), foodAdapter);
                    recycler_menu.setAdapter(mergeAdapter);
                } else {
                    ((FoodAdapter)((MergeAdapter) adapter).getAdapters().get(1))
                            .updateOptions(food_options);
                }
            } else holder.category_image.animate().rotation(0F).start();

        }
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
                final Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
                final Point size = new Point();
                defaultDisplay.getSize(size);
                final int height = (int) ((size.y * 0.5) - (size.y * 0.36));
                View view = new View(context);
                view.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, height));
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

    class CategoryHolder extends RecyclerView.ViewHolder {

        ImageView category_image;
        TextView category_text;
        View v;

        CategoryHolder(@NonNull View itemView) {
            super(itemView);
            category_image = itemView.findViewById(R.id.category_image);
            category_text = itemView.findViewById(R.id.category_name);
            v = itemView;
        }

        ItemDetailsLookup.ItemDetails<String> getItemDetails()  {
            return new ItemDetailsLookup.ItemDetails<String>() {
                @Override
                public int getPosition() {
                    return getAdapterPosition();
                }

                @Nullable
                @Override
                public String getSelectionKey() {
                    return getItem(getAdapterPosition()).getCategoryID();
                }
                @Override
                public boolean inSelectionHotspot(@NotNull MotionEvent e) {
                    return true;
                }
            };
        }
    }
}
