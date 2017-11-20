package com.example.vincent.to_dolist;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.EditText;


public class ToDoDatabase extends SQLiteOpenHelper {
    private static ToDoDatabase instance;
    private static final String DBName = "toDos.db";
    private static final int version = 1;
    private ToDoDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public static ToDoDatabase getInstance(Context context){
        if(instance == null){
            instance = new ToDoDatabase(context.getApplicationContext(),DBName,null , version);
        }
        return instance;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table todos (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                               "title TEXT, completed INTEGER);");


    }

    public void insert(String title, int completed) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("completed", completed);
        db.insert("todos",null,values);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + "todos");
        onCreate(db);
    }
    public Cursor selectAll(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor =db.rawQuery("SELECT * FROM todos",null);

        return cursor;
    }
    public void update(long id, int completed){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("completed", completed);
        db.update("todos",values,"_id="+id, null);

    }

    public void delete(long id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("todos","_id="+id,null);

    }
}
