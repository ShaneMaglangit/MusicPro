package com.example.musicpro.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.musicpro.data.VenueContract.VenueEntry;

public class VenueDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "venue.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + VenueEntry.TABLE_NAME + " (" +
                    VenueEntry._ID + " LONG PRIMARY KEY," +
                    VenueEntry.COLUMN_NAME_NAME + " TEXT," +
                    VenueEntry.COLUMN_NAME_ADDRESS + " TEXT," +
                    VenueEntry.COLUMN_NAME_OPENING_TIME + " TEXT," +
                    VenueEntry.COLUMN_NAME_UPDATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + VenueEntry.TABLE_NAME;

    public VenueDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
