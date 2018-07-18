package com.andyshon.journal.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.andyshon.journal.FragmentItems.Gallery;
import com.andyshon.journal.FragmentItems.Note;
import com.andyshon.journal.Models.Post;

import java.util.ArrayList;
import java.util.List;

import static com.andyshon.journal.database.DatabaseHelper.*;

/**
 * Created by andyshon on 17.07.18.
 */

public class Database {

    private DatabaseHelper databaseHelper;


    public Database(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }


    public void close() {
        databaseHelper.close();
    }


    /*
    * Creating Post
    * */
    public long createPost (Post post) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, post.getName());
        values.put(KEY_IMAGE, post.getImage());
        //values.put(POSITION!)

        // insert row
        long id = db.insert(TABLE_POSTS, null, values);
        System.out.println("LONG ID:" + id);
        return id;
    }


    /*
    * Updating Post
    * */
    public int updatePost (Post post) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, post.getName());
        values.put(KEY_IMAGE, post.getImage());

        // updating row
        return db.update(TABLE_POSTS, values, KEY_ID + " = ?", new String[] { String.valueOf(post.getId()) });
    }


    /*
    * Deleting Post
    * */
    public void deletePost (long post_id) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.delete(TABLE_POSTS, KEY_ID + " = ?", new String[]{String.valueOf(post_id)});
    }


    /*
 * getting all todos
 * */
    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<Post>();
        String selectQuery = "SELECT * FROM " + TABLE_POSTS;

        Log.e("LOG", selectQuery);

        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        System.out.println("CURSOR COUNT: " + c.getColumnCount() + ":" + c.getCount());

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {

                byte[] mas = c.getBlob(c.getColumnIndex(KEY_IMAGE));
                Post post = new Post(Integer.parseInt(c.getString(c.getColumnIndex(KEY_ID))), c.getString(c.getColumnIndex(KEY_NAME)), mas);
                // adding to post list
                posts.add(post);
            } while (c.moveToNext());
        }

        return posts;
    }



    public List<Gallery> getAllGalleries() {
        List<Gallery> posts = new ArrayList<Gallery>();
        String selectQuery = "SELECT * FROM " + TABLE_GALLERIES;

        Log.e("LOG", selectQuery);

        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {

                byte[] mas = c.getBlob(c.getColumnIndex(KEY_IMAGE));

                Gallery td = new Gallery(null, "p2");
                td.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                td.setPosition(c.getInt(c.getColumnIndex(KEY_POSITION)));
                td.setPost_id((c.getInt(c.getColumnIndex(KEY_POST_ID))));
                td.setImage(mas);

                // adding to todo list
                posts.add(td);
            } while (c.moveToNext());
        }

        return posts;
    }



    /*
    * Creating Gallery
    * */
    public void createGallery (Gallery gallery) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_IMAGE, gallery.getImageUri());
        values.put(KEY_POST_ID, gallery.getPost_id());
        values.put(KEY_POSITION, gallery.getPosition());
        values.put(KEY_TIMESTAMP, gallery.getTimestamp());

        // insert row
        long gallery_id = db.insert(TABLE_GALLERIES, null, values);
        System.out.println("GALLERY ID:" + gallery_id);
    }


    /*
    * Creating Note
    * */
    public void createNote (Note note) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, note.getName());
        values.put(KEY_POST_ID, note.getPost_id());
        values.put(KEY_POSITION, note.getPosition());
        values.put(KEY_TIMESTAMP, note.getTimestamp());

        // insert row
        long note_id = db.insert(TABLE_NOTES, null, values);
        System.out.println("NOTE ID:" + note_id);
    }



    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<Note>();
        String selectQuery = "SELECT * FROM " + TABLE_NOTES;

        Log.e("LOG", selectQuery);

        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {

                Note note = new Note(null,"p2");
                note.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                note.setPosition(c.getInt(c.getColumnIndex(KEY_POSITION)));
                note.setPost_id((c.getInt(c.getColumnIndex(KEY_POST_ID))));
                note.setName(c.getString(c.getColumnIndex(KEY_NAME)));

                // adding to todo list
                notes.add(note);
            } while (c.moveToNext());
        }

        return notes;
    }
}
