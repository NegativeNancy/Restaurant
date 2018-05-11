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


/* Class that queries an URL for JsonObjects and returns ans Json array to the requester. */
class CategoriesRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    private final Context globalContext;
    private Callback globalActivity;

    /* Callback to be used by other activities */
    public interface Callback {
        void gotCategories(ArrayList<String> categories);
        void gotCategoriesError(String message);
    }

    /* Store the context of the request. */
    public CategoriesRequest(Context context) {
        globalContext = context;
    }

    /* Query the specified URL for an JSON object. */
    public void getCategories(Callback activity) {
        globalActivity = activity;
        RequestQueue queue = Volley.newRequestQueue(globalContext);
        String URL_CATEGORIES = "https://resto.mprog.nl/categories";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URL_CATEGORIES,null,
                this, this);
        queue.add(jsonObjectRequest);
    }

    /* On successful JSON response store data in JSON String Array. */
    @Override
    public void onResponse(JSONObject response) {
        ArrayList<String> categoryList = new ArrayList<>();

        try {
            JSONArray jsonArray = response.getJSONArray("categories");

            for (int i = 0, l = jsonArray.length(); i < l; i++) {
                categoryList.add(jsonArray.getString(i));
            }

            globalActivity.gotCategories(categoryList);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* On error return error message. */
    @Override
    public void onErrorResponse(VolleyError error) {
        globalActivity.gotCategoriesError(error.getMessage());
    }
}
