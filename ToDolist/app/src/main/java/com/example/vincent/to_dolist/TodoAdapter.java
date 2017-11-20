package com.example.vincent.to_dolist;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

public class TodoAdapter extends CursorAdapter {
    public TodoAdapter(Context context, Cursor cursor) {
        super(context, cursor, R.layout.row_layout);

    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.row_layout, parent, false);
    }
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView item = view.findViewById(R.id.item);
        CheckBox complete = view.findViewById(R.id.complete);
        item.setText(cursor.getString(cursor.getColumnIndex("title")));
        if (cursor.getInt(cursor.getColumnIndex("completed"))==1){
            complete.setChecked(true);
        }
        else{
            complete.setChecked(false);
        }

    }

}
