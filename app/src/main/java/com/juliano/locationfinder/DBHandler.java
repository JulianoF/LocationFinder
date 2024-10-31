package com.juliano.locationfinder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "locationDatabase";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_LOCATIONS = "location";

    private static final String KEY_ID = "id";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";


    public DBHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTES_TABLE = "CREATE TABLE " + TABLE_LOCATIONS +
                "(" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_ADDRESS + " TEXT," +
                KEY_LATITUDE + " REAL," +
                KEY_LONGITUDE + " REAL" +
                ")";

        db.execSQL(CREATE_NOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATIONS);
            onCreate(db);
        }
    }

    public void addLocation(Location loc){
        SQLiteDatabase db = getWritableDatabase();
        String address = loc.getAddress();
        double latitude = loc.getLatitude();
        double longitude = loc.getLongitude();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_ADDRESS,address);
            values.put(KEY_LATITUDE,latitude);
            values.put(KEY_LONGITUDE,longitude);
            db.insertOrThrow(TABLE_LOCATIONS, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {

        } finally {
            db.endTransaction();
        }

    }
    public void updateLocation(Location loc){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ADDRESS, loc.getAddress());
        values.put(KEY_LATITUDE, loc.getLatitude());
        values.put(KEY_LONGITUDE, loc.getLongitude());

        db.update(TABLE_LOCATIONS, values, KEY_ID + " = ?", new String[] { String.valueOf(loc.getId()) });
        db.close();
    }

    public void deleteLocation(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOCATIONS, KEY_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public Cursor getAllLocations() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_LOCATIONS;
        Cursor cursor;
        try {
            cursor = db.rawQuery(query, null);
        } catch (Exception e) {
            cursor = null;
        }
        return cursor;
    }

    public Cursor searchLocations(String userInput) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_LOCATIONS + " WHERE " + KEY_ADDRESS + " LIKE ?";
        Cursor cursor;
        try {
            cursor = db.rawQuery(query, new String[]{"%" + userInput + "%"});
        } catch (Exception e) {
            cursor = null;
        }
        return cursor;
    }


}
