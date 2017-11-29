package com.example.vincent.restaurantrevisited;
import android.content.Context;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

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
import java.util.Objects;


public class MenuFragment extends ListFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        return view;
    }

    private class addOrder implements AdapterView.OnItemClickListener {
        @Override
        //makes a onItemClick event to add a meal to your order
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Context context = getContext();
            String text = String.valueOf(adapterView.getItemAtPosition(i));
            CharSequence msg = "Your meal has been added to your order";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, msg, duration);
            toast.show();
            for (int s = 0; s < Items.size(); s++) {
                try {
                    if (Objects.equals(Items.get(s).getString("name"), text.toString())) {
                        RestoDatabase db = RestoDatabase.getInstance(getContext());
                        db.insert(Items.get(s).getString("name"),
                                Items.get(s).getInt("price"), 1);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    ArrayList<JSONObject> Items= new ArrayList<JSONObject>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = this.getArguments();
        // Get the wanted category
        final String category = arguments.getString("category");
        // Uses the category to request the menu
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "https://resto.mprog.nl/menu";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList results = new ArrayList();
                        try {
                            results = new ArrayList<JSONObject>();
                            JSONObject object =
                                    (JSONObject) new JSONTokener(response).nextValue();
                            JSONArray subArray = object.getJSONArray("items");
                            for (int i = 0; i < subArray.length(); i++) {
                                if (Objects.equals(
                                        subArray.getJSONObject(i).getString("category")
                                        , category)) {
                                    results.add(
                                        subArray.getJSONObject(i).getString("name"));
                                    Items.add(subArray.getJSONObject(i));
                                }
                            }
                            setAdapter(results);
                            getListView().setOnItemClickListener(new addOrder());
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
    // Sets a list adapter
    public void setAdapter(ArrayList<String> results){
        this.setListAdapter(new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, results ));
    }
}

