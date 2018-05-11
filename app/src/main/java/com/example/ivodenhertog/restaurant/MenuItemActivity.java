package com.example.ivodenhertog.restaurant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/*  */
public class MenuItemActivity extends AppCompatActivity {

    /* Create view based on item that has been clicked. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item);

        Intent intent = getIntent();
        MenuItem menuItem = (MenuItem) intent.getSerializableExtra("menuItemClicked");

        loadMenuItem(menuItem);
    }

    /* Load the information of the item that has been clicked */
    private void loadMenuItem(MenuItem menuItem) {
        TextView itemName = findViewById(R.id.itemName);
        ImageView itemImage = findViewById(R.id.itemImage);
        final ProgressBar itemProgress = findViewById(R.id.itemImageProgress);
        final TextView itemDesc = findViewById(R.id.itemDescription);
        TextView itemPrice = findViewById(R.id.itemPrice);

        // Set name, description and price.
        itemName.setText(menuItem.getName());
        itemDesc.setText(menuItem.getDescription());
        String price = this.getResources().getString(R.string.currency) +
                menuItem.getPrice();
        itemPrice.setText(price);

        // Create URL and load image in ImageView with Picasso library.
        String url = menuItem.getImageUrl();
        Picasso.get().load(url).into(itemImage, new Callback() {
            @Override
            public void onSuccess() {
                itemProgress.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
            }
        });
    }

    /* Finish the activity when button is pressed. */
    public void closeClicked() {
        finishAfterTransition();
    }
}
