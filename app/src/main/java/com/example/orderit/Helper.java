package com.example.orderit;

import android.content.Context;

import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

public final class Helper {
    public static CircularProgressDrawable circularProgressDrawableOf(Context context) {
        CircularProgressDrawable circularProgressDrawable =
                new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5F);
        circularProgressDrawable.setCenterRadius(30F);
        circularProgressDrawable.start();
        return circularProgressDrawable;
    }
}
