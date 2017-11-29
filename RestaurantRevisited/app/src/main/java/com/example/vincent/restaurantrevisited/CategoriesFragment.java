package com.example.vincent.restaurantrevisited;


import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;



public class CategoriesFragment extends ListFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Creates a request
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "https://resto.mprog.nl/categories";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            ArrayList<String> results = new ArrayList<String>();
                            JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
                            JSONArray subArray = object.getJSONArray("categories");
                            for (int i = 0; i < subArray.length(); i++) {
                                results.add( subArray.get(i).toString());
                            }
                            setAdapter(results);
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);
    }
    //Makes a list adapter
    public void setAdapter(ArrayList<String> results){
        this.setListAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, results ));
    }

    @Override
    // Overrides the onListItemClick
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        MenuFragment menuFragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putString("category", String.valueOf(l.getItemAtPosition(position)));
        menuFragment.setArguments(args);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, menuFragment)
                .addToBackStack(null)
                .commit();
    }
}
