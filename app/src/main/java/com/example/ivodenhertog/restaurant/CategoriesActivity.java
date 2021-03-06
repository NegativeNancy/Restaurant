package com.example.ivodenhertog.restaurant;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/* Main view with the food categories that the restaurant uses. */
public class CategoriesActivity extends AppCompatActivity implements CategoriesRequest.Callback {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        // Make request to get the categories.
        CategoriesRequest request = new CategoriesRequest(getApplicationContext());
        request.getCategories(this);

        // Create onClick listener for categories
        listView = findViewById(R.id.mainList);
        itemClicked listener = new itemClicked();
        listView.setOnItemClickListener(listener);
    }

    /* Save instance state when config change happens. */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        listView = findViewById(R.id.mainList);
        int scrollPosition = listView.getLastVisiblePosition();
        outState.putInt("scroll_position",  scrollPosition);
    }

    /* Restore state after config change. */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        listView = findViewById(R.id.mainList);
        final int savedPosition = savedInstanceState.getInt("scroll_position");
        listView.smoothScrollToPosition(savedPosition);
    }

    /* Use adapter to fill the list. */
    @Override
    public void gotCategories(ArrayList<String> categories) {
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<>(this,
                R.layout.categorie_row, R.id.categoryTitle, categories);
        ListView listView = findViewById(R.id.mainList);
        listView.setAdapter(categoriesAdapter);
    }

    /* Display error message when error occurs. */
    @Override
    public void gotCategoriesError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    /* Implements OnItemClickListener for categories. */
    private class itemClicked implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String clickedCategory = (String) parent.getItemAtPosition(position);

            Intent menuActivity = new Intent(CategoriesActivity.this, MenuActivity.class);
            menuActivity.putExtra("clickedCategory", clickedCategory);

            // Create Parent-to-child transition as specified by Material Design
            Bundle options = ActivityOptionsCompat.makeScaleUpAnimation(view, 0,0, view.getWidth(), view.getHeight()).toBundle();
            startActivity(menuActivity, options);
        }
    }
}
