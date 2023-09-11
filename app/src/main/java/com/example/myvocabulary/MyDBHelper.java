package com.example.myvocabulary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDBHelper {
    public static final String KEY_TITLE = "title";
    public static final String KEY_WORD = "word";
    public static final String KEY_MEANING = "meaning";
    public static final String KEY_ROWID = "_id";
    private static final String TAG = "MyDBHelper";

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    /**
     *
     * Database creation sql statement
     */
    private static final String DATABASE_CREATE = "create table notes (_id integer primary key autoincrement, "
            + "title text not null, word text not null, meaning text not null);";
    private static final String DATABASE_NAME = "WordDB";
    private static final String DATABASE_TABLE = "notes";
    private static final int DATABASE_VERSION = 2;
    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion
                    + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS notes");
            onCreate(db);
        }
    }

    public MyDBHelper(Context ctx) {
        this.mCtx = ctx;
    }

    public MyDBHelper open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public long createNote(String title, String word, String meaning) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TITLE, title);
        initialValues.put(KEY_WORD, word);
        initialValues.put(KEY_MEANING, meaning);
        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteNote(long rowId) {
        Log.i("Delete called", "value__" + rowId);
        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }


    public Cursor fetchAllNotes() {
        return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TITLE, KEY_WORD, KEY_MEANING }
        , null, null, null, null, null);
    }

    public Cursor fetchNote(long rowId) throws SQLException {

        Cursor mCursor = mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TITLE, KEY_WORD, KEY_MEANING }, KEY_ROWID
                + "=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor fetchNeedNotes(String title){
        Cursor mCursor = mDb.rawQuery("SELECT * FROM notes WHERE title ='" + title + "';",null);
        return mCursor;
    }

    public Cursor fetchTitleNotes(){
        return mDb.rawQuery("SELECT DISTINCT title FROM notes",null);
    }

    public boolean updateNote(long rowId, String title, String word, String meaning) {
        ContentValues args = new ContentValues();
        args.put(KEY_TITLE, title);
        args.put(KEY_WORD, word);
        args.put(KEY_MEANING, meaning);
        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

}
