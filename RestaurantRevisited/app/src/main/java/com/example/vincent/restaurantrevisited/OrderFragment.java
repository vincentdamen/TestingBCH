package com.example.vincent.restaurantrevisited;


import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class OrderFragment extends DialogFragment implements View.OnClickListener {
    private RestoAdapter adapter;
    private RestoDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        ListView list = (ListView) view.findViewById(R.id.List1);
        TextView total = (TextView) view.findViewById(R.id.total);
        db = RestoDatabase.getInstance(getContext());
        Cursor overview = db.selectAll();
        adapter = new RestoAdapter(getActivity(), overview);
        list.setAdapter(adapter);
        String totalText = getTotal(overview);
        total.setText(totalText);
        Button clear = view.findViewById(R.id.clearOrder);
        clear.setOnClickListener(this);
        Button send = view.findViewById(R.id.orderButton);
        send.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case(R.id.clearOrder):
                db.Clear();
                Context context = getContext();
                CharSequence text = "Your order has been deleted";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                updateData();
            case(R.id.orderButton):
                db.Clear();
                Context context1 = getContext();
                CharSequence text1 = "Your order has been send";
                int duration1 = Toast.LENGTH_SHORT;
                Toast toast1 = Toast.makeText(context1, text1, duration1);
                toast1.show();
                updateData();
        }
    }
    // Updates the data with swapping cursors
    public void updateData(){
        db = RestoDatabase.getInstance(getContext());
        Cursor overview1 = db.selectAll();
        adapter.swapCursor(overview1);
        ListView list = (ListView) getView().findViewById(R.id.List1);
        TextView total = (TextView) getView().findViewById(R.id.total);
        String totalText = getTotal(overview1);
        total.setText(totalText);
        list.setAdapter(adapter);
    }
    // Prepares total text
    public String getTotal(Cursor cursor){
        int total =0;
        while(cursor.moveToNext()){
            int sum = cursor.getInt(cursor.getColumnIndex("amount")) *
                    cursor.getInt(cursor.getColumnIndex("price"));
            total += sum ;
        }
        String output = "Total: € "+total;
        return output;
    }
}
