package com.cuboid.gettingstarted.savedata.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cuboid.gettingstarted.savedata.sql.FeedReaderContract.FeedEntry;

/**
 * Created by root on 1/15/15.
 */
public class EntryDatabase {
    /**
     * To access your database, instantiate your subclass of SQLiteOpenHelper:
     */
    public static FeedReaderDbHelper createDatabase(Context context) {
        return new FeedReaderDbHelper(context);
    }

    /**
     * Insert data into the database by passing a
     * ContentValues object to the insert() method:
     */
    public static long putInformation(FeedReaderDbHelper mDbHelper,
                                      int id,
                                      String title,
                                      String content) {// Gets the data repository in write mode
        //Because they can be long-running, be sure that you call
        // getWritableDatabase() or getReadableDatabase()
        // in a background thread, such as with AsyncTask or IntentService.
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_ENTRY_ID, id);
        values.put(FeedEntry.COLUMN_NAME_TITLE, title);
        values.put(FeedEntry.COLUMN_NAME_CONTENT, content);

// Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                FeedEntry.TABLE_NAME,
                //insert NULL if ContentValues is empty
                // (if use null then the framework will
                // not insert a row when there are no values).
                FeedEntry.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }

    /**
     * To read from a database, use the query() method,
     * passing it your selection criteria and desired columns.
     * The method combines elements of insert() and update(),
     * except the column list defines the data you want to fetch,
     * rather than the data to insert.
     * <p/>
     * The results of the query are returned to you in a Cursor object.
     */
    public static Cursor readInformation(
            FeedReaderDbHelper mDbHelper,
            String selection,
            String... selectionArgs) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                FeedEntry._ID,
                FeedEntry.COLUMN_NAME_TITLE,
                FeedEntry.COLUMN_NAME_UPDATED
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                FeedEntry.COLUMN_NAME_UPDATED + " DESC";

        Cursor c = db.query(
                FeedEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        /**
         * To look at a row in the cursor, use one of the Cursor move methods,
         * which you must always call before you begin reading values.
         * Generally, you should start by calling moveToFirst(),
         * which places the "read position" on the first entry in the results.
         * For each row, you can read a column's value by calling one of the Cursor
         * get methods, such as getString() or getLong(). For each of the get methods,
         * you must pass the index position of the column you desire, which you can
         * by calling getColumnIndex() or getColumnIndexOrThrow(). For example:

         cursor.moveToFirst();
         long itemId = cursor.getLong(
         cursor.getColumnIndexOrThrow(FeedEntry._ID)
         );
         */

        return c;
    }

    /**
     * To delete rows from a table, you need to provide selection criteria
     * that identify the rows. The database API provides a mechanism for
     * creating selection criteria that protects against SQL injection.
     * The mechanism divides the selection specification into a selection
     * clause and selection arguments. The clause defines the columns to look
     * at, and also allows you to combine column tests. The arguments are
     * values to test against that are bound into the clause. Because the
     * result isn't handled the same as a regular SQL statement, it is
     * immune to SQL injection.
     * <p/>
     * * @return the number of rows affected if a whereClause is passed in, 0
     * otherwise. To remove all rows and get a count pass "1" as the
     * whereClause.
     */
    public static int deleteRowFromTable(SQLiteDatabase db, int rowId, String table_name) {// Define 'where' part of query.
        String selection = FeedEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
// Specify arguments in placeholder order.
        String[] selectionArgs = {String.valueOf(rowId)};
// Issue SQL statement.
        int rowsAffected = db.delete(table_name, selection, selectionArgs);
        return rowsAffected;
    }

    /**
     * When you need to modify a subset of your database values, use the update() method.
     * <p/>
     * Updating the table combines the content values syntax of insert() with the where syntax of delete().
     */
    public static void UpdateDatabase(FeedReaderDbHelper mDbHelper,
                                      String title,
                                      int rowId) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_TITLE, title);

// Which row to update, based on the ID
        String selection = FeedEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(rowId)};

        int count = db.update(
                FeedEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }
}
