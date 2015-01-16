package com.cuboid.gettingstarted.savedata.sql;

import com.cuboid.gettingstarted.savedata.sql.FeedReaderContract.FeedEntry;

/**
 * @author Kyle D. Williams<kyledgwilliams@gmail.com>
 */
public class SqlHelper {
    protected static final String TEXT_TYPE = " TEXT";
    protected static final String COMMA_SEP = ",";
    protected static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    // Any other options for the CREATE command
                    " )";

    protected static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
}
