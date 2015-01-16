package com.cuboid.gettingstarted.savedata.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * To access your database, instantiate your subclass of SQLiteOpenHelper:
 * <p/>
 * FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(getContext());
 * <p/>
 * Because they can be long-running, be sure that you call getWritableDatabase()
 * or getReadableDatabase() in a background thread, such as with AsyncTask or IntentService.
 *
 * @author Kyle D. Williams<kyledgwilliams@gmail.com>
 */
public class FeedReaderDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SqlHelper.SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SqlHelper.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}