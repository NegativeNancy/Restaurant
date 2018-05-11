package com.example.ivodenhertog.restaurant;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/* Activity that displays al the items in the specific category */
public class MenuActivity extends AppCompatActivity implements MenuItemsRequest.Callback {

    /* Create activity and fill the ListView. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // get category that is clicked
        Intent intent = getIntent();
        String category = intent.getStringExtra("clickedCategory");

        // create new request for specified category
        MenuItemsRequest request = new MenuItemsRequest(this, category);
        request.getMenuItems(this);

        ListView listView = findViewById(R.id.menuList);
        itemClicked listener = new itemClicked();
        listView.setOnItemClickListener(listener);
    }

    /* Save instance state when config change happens. */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ListView listView = findViewById(R.id.menuList);
        int scrollPosition = listView.getFirstVisiblePosition();
        outState.putInt("scroll_position",  scrollPosition);
    }

    /* Restore state after config change. */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ListView listView = findViewById(R.id.menuList);
        final int savedPosition = savedInstanceState.getInt("scroll_position");
        listView.smoothScrollToPosition(savedPosition);
    }

    /* Use adapter to fill the list. */
    @Override
    public void gotMenuItems(ArrayList<MenuItem> menuItems) {
        MenuAdapter menuAdapter = new MenuAdapter(this, menuItems);
        ListView listView = findViewById(R.id.menuList);
        listView.setAdapter(menuAdapter);
    }

    /* Display error in an Toast. */
    @Override
    public void gotMenuItemsError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    /* Implements OnItemClickListener for the menu items. */
    private class itemClicked implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            MenuItem menuItem = (MenuItem) parent.getItemAtPosition(position);

            Intent menuItemActivity = new Intent(MenuActivity.this, MenuItemActivity.class);
            menuItemActivity.putExtra("menuItemClicked", menuItem);

            // Create Parent-to-child transition as specified by Material Design
            Bundle options = ActivityOptionsCompat.makeScaleUpAnimation(view, 0,0, view.getWidth(), view.getHeight()).toBundle();
            startActivity(menuItemActivity, options);
        }
    }
}
