package com.example.vincent.restaurantrevisited;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
public class RestoAdapter extends CursorAdapter {
    public RestoAdapter(Context context, Cursor cursor) {
        super(context, cursor, R.layout.row_layout);

    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.row_layout, parent,
                false);
    }
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView item = view.findViewById(R.id.item);
        TextView amount = view.findViewById(R.id.amount);
        TextView price = view.findViewById(R.id.price);
        item.setText(cursor.getString(cursor.getColumnIndex("name")));
        amount.setText(cursor.getString(cursor.getColumnIndex("amount")));
        int sum = cursor.getInt(cursor.getColumnIndex("amount")) *
                cursor.getInt(cursor.getColumnIndex("price"));
        price.setText("€"+sum);

    }

}
