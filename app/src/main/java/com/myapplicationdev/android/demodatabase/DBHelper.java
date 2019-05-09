package com.myapplicationdev.android.demodatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

//    Start version with 1
//    increment by 1 whenever db schema changes
//    These are constants
    //Version number
    private static final int DATABASE_VER = 1;
//    Filename of the database
    //Database name
    private static final String DATABASE_NAME = "tasks.db";

    // These are constants
    private static final String TABLE_TASK = "task";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DATE = "date";

    public DBHelper(Context context) {
        // Constructor of super class
        //Checks whether the database exist or not
        //If not it will run the onCreate method.
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    // SQL statement to create the table needs to be executed here.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSql = "CREATE TABLE " + TABLE_TASK + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_DATE + " TEXT," + COLUMN_DESCRIPTION + " TEXT )";
        db.execSQL(createTableSql);
        Log.i("info", "created tables");
    }

    // It will call onUpgrade() if the database version has changed
    // For simplicity , we just delete the existing database and create new one
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        //Create table(s) again
        onCreate(db);
    }

    // Insert a new note to the table.
    // Can create our own method name for this. (insertTask)
    // values.put don't have to put for id as it is auto increment
    public void insertTask(String description, String date){

        // Get an instance of the database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        // Use ContentValues object to store the values for db operation
        ContentValues values = new ContentValues();
        // Store the column name as key and the description as value
        values.put(COLUMN_DESCRIPTION, description);
        // Store the column name as key and the date as value
        values.put(COLUMN_DATE, date);
        // Insert the row into the TABLE_TASK
        db.insert(TABLE_TASK, null, values);
        // Close the database connection
        db.close();

    }


    public ArrayList<Task> getTasks(){
        ArrayList<Task> tasks = new ArrayList<Task>();
        String selectQuery = "SELECT " + COLUMN_ID + ", " + COLUMN_DESCRIPTION + ", " + COLUMN_DATE + " FROM " + TABLE_TASK;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String description = cursor.getString(1);
                String date = cursor.getString(2);
                Task obj = new Task(id, description, date);
                tasks.add(obj);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tasks;
    }

//    public ArrayList<String> getTaskContent(){
//
//        // Create an ArrayList that holds String objects
//        ArrayList<String> tasks = new ArrayList<String>();
//        // Select all the tasks' description
//        String selectQuery = "SELECT " + COLUMN_DESCRIPTION + " FROM " + TABLE_TASK;
//
//        // Get the instance of database to read
//        SQLiteDatabase db = this.getReadableDatabase();
//        // Run the SQL query and get back the Cursor object
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // moves to first row
//        if(cursor.moveToFirst()){
//            // the while loop points to the next row
//            // It will return true if there is next row and will do the same thing as first row
//            // If will return false when no more next row and will close connection
//            do{
//                tasks.add(cursor.getString(0));
//            }while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//        return tasks;
//    }
}


