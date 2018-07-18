package com.andyshon.journal.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by andyshon on 17.07.18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "Journal-alpha";

    // Table Names
    static final String TABLE_POSTS = "Posts";
    static final String TABLE_NOTES = "Notes";
    static final String TABLE_GALLERIES = "Galleries";

    // Column names
    static final String KEY_ID = "id";
    static final String KEY_POST_ID = "post_id";
    static final String KEY_POSITION = "position";
    static final String KEY_NAME = "name";
    static final String KEY_TIMESTAMP = "timestamp";
    static final String KEY_IMAGE = "image";


    // Table Create Statements
    // Todo table create statement
    private static final String CREATE_TABLE_POSTS = "CREATE TABLE "
            + TABLE_POSTS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME
            + " TEXT," + KEY_IMAGE + " BLOB" + ")";

    // Tag table create statement
    private static final String CREATE_TABLE_NOTES = "CREATE TABLE "
            + TABLE_NOTES + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_POST_ID
            + " INTEGER," + KEY_POSITION + " INTEGER," + KEY_NAME
            + " TEXT," + KEY_TIMESTAMP + " TEXT" + ")";

    // todo_tag table create statement
    private static final String CREATE_TABLE_GALLERIES = "CREATE TABLE "
            + TABLE_GALLERIES + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_POST_ID
            + " INTEGER," + KEY_POSITION + " INTEGER," + KEY_NAME
            + " TEXT," + KEY_IMAGE + " BLOB," + KEY_TIMESTAMP + " TEXT" + ")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_POSTS);
        db.execSQL(CREATE_TABLE_NOTES);
        db.execSQL(CREATE_TABLE_GALLERIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GALLERIES);

        // create new tables
        onCreate(db);
    }
}
