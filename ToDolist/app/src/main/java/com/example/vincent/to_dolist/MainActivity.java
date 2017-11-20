package com.example.vincent.to_dolist;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TodoAdapter adapter;
    private ToDoDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = ToDoDatabase.getInstance(getApplicationContext());
        setContentView(R.layout.activity_main);
        Cursor overview = db.selectAll();
        adapter = new TodoAdapter( this,overview);
        if(adapter.getCount()<1){
            db.insert("Do the dishes",0);
            db.insert("Do the laundry",1);
            db.insert("Get food for tonight",0);
        }
        ListView list = (ListView) findViewById(R.id.List);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new shortPress());
        list.setOnItemLongClickListener(new longPress());

    }

    public class shortPress implements AdapterView.OnItemClickListener{


        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            CheckBox complete = view.findViewById(R.id.complete);
            Cursor overview = db.selectAll();
            overview.move(i+1);
            int id= overview.getInt(overview.getColumnIndex("_id"));
            if(complete.isChecked()){
                db.update(id, 0);

            }
            else{
                db.update(id,1);
            }
            updateData();
        }

    }
    private class longPress implements AdapterView.OnItemLongClickListener{
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            Cursor overview = db.selectAll();
            overview.move(i+1);
            int id= overview.getInt(overview.getColumnIndex("_id"));
            db.delete(id);
            updateData();
            return true;
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        EditText editor = (EditText) findViewById(R.id.Editor);
        String text = editor.getText().toString();
        outState.putString("editor",text);
    }

    @Override
    public void onRestoreInstanceState(Bundle inState){
        super.onRestoreInstanceState(inState);
        EditText editor = (EditText) findViewById(R.id.Editor);
        String text = inState.getString("editor");
        editor.setText(text);
    }

    public void addItem(View view){
        EditText editor = (EditText) findViewById(R.id.Editor);
        String ToDo = editor.getText().toString();
        editor.setText("");
        if(ToDo.length()>0){
        ToDoDatabase db = ToDoDatabase.getInstance(getApplicationContext());
        db.insert(ToDo,0);
        updateData();}
    }

    private void updateData(){
        db = ToDoDatabase.getInstance(getApplicationContext());
        Cursor overview1 = db.selectAll();
        adapter.swapCursor(overview1);
        ListView list = (ListView) findViewById(R.id.List);
        list.setAdapter(adapter);
    }


}
