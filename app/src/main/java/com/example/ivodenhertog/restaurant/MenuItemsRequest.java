package com.example.ivodenhertog.restaurant;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/* Class that queries an URL for JsonObjects and returns an ArrayList to the requester. */
class MenuItemsRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    private final Context globalContext;
    private MenuItemsRequest.Callback globalActivity;
    private final String globalCategory;

    /* Callback to be used by other activities */
    public interface Callback {
        void gotMenuItems(ArrayList<MenuItem> menuItemsList);
        void gotMenuItemsError(String message);
    }

    /* Globalize context and category for later use. */
    public MenuItemsRequest(Context context, String category) {
        globalContext = context;
        globalCategory = category;
    }

    /* Query the specified URL for an JSON object. */
    public void getMenuItems(Callback activity) {
        globalActivity = activity;
        RequestQueue queue = Volley.newRequestQueue(globalContext);
        String url = "https://resto.mprog.nl/menu?category=" + globalCategory;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url,null,
                this, this);
        queue.add(jsonObjectRequest);
    }

    /* On successful JSON response store data in an ArrayList<MenuItems>. */
    @Override
    public void onResponse(JSONObject response) {
        ArrayList<MenuItem> menuItemsList = new ArrayList<>();

        try {
            JSONArray jsonArray = response.getJSONArray("items");
            for (int i = 0, l = jsonArray.length(); i < l; i++) {
                JSONObject menuObject = jsonArray.getJSONObject(i);

                // Get strings form the JSON Object.
                String category = menuObject.getString("category");
                String description = menuObject.getString("description");
                String price = menuObject.getString("price");
                String imageUrl = menuObject.getString("image_url");
                String name = menuObject.getString("name");

                // Store the values in the MenuItem model.
                MenuItem menuItem = new MenuItem(name, description, imageUrl, price, category);
                menuItemsList.add(menuItem);
            }

            globalActivity.gotMenuItems(menuItemsList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* On error return error message. */
    @Override
    public void onErrorResponse(VolleyError error) {
        globalActivity.gotMenuItemsError(error.getMessage());
    }

}
