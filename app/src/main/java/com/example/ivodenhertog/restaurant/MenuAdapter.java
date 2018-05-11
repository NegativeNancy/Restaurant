package com.example.ivodenhertog.restaurant;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/* Custom adapter to fill the MenuItemsActivity with all the required information. */
class MenuAdapter extends ArrayAdapter<MenuItem> {

    private final ArrayList<MenuItem> menuItems;
    private final Context globalContext;

    /* Globalize object and context for later use. */
    MenuAdapter(@NonNull Context context, @NonNull ArrayList<MenuItem> objects) {
        super(context, R.layout.menu_row, objects);
        menuItems = objects;
        globalContext = context;
    }

    /* Use global information to fill the views with the correct information. */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.menu_row, parent, false);
        }

        // Create views to display in grid view.
        ImageView menuImage = convertView.findViewById(R.id.imageView);
        TextView menuName = convertView.findViewById(R.id.nameView);
        TextView menuPrice = convertView.findViewById(R.id.priceView);
        final ProgressBar imgProgress = convertView.findViewById(R.id.imgProgress);

        MenuItem menuItem = menuItems.get(position);

        // Create URL and load image in ImageView with Picasso library.
        String url = menuItem.getImageUrl();
        Picasso.get().load(url).into(menuImage, new Callback() {
            /* Hide loading bar when image is loaded. */
            @Override
            public void onSuccess() {
                imgProgress.setVisibility(View.GONE);
            }
            /* No custom error message, this keeps the loading bar active. */
            @Override
            public void onError(Exception e) {
            }
        });

        // Set name and price.
        menuName.setText(menuItem.getName());
        String price = globalContext.getResources().getString(R.string.currency) +
                menuItem.getPrice();
        menuPrice.setText(price);

        return convertView;
    }
}
