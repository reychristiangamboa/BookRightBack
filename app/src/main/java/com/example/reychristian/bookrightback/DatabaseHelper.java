package com.example.reychristian.bookrightback;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * Created by Rey Christian on 11/14/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String SCHEMA = "BRB";
    public static final int VERSION = 1;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static final String TABLE_NAME_BORROW_HISTORY = "borrowHistory";
    public static final String BH_COLUMN_ID = "_id";
    public static final String BH_COLUMN_BOOK_ID = "bookId";
    public static final String BH_COLUMN_LIBRARY = "library";
    public static final String BH_COLUMN_DATEBORROWED = "dateBorrowed";
    public static final String BH_COLUMN_DATEDUE = "dateDue";
    public static final String BH_COLUMN_DATERETURNED = "dateReturned";

    public static final String SQL_CREATE_TABLE_BORROW_HISTORY = "CREATE TABLE " + TABLE_NAME_BORROW_HISTORY + " ("
            + BH_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + BH_COLUMN_BOOK_ID + " INTEGER NOT NULL,"
            + BH_COLUMN_LIBRARY + " TEXT NOT NULL,"
            + BH_COLUMN_DATEBORROWED + " TEXT,"
            + BH_COLUMN_DATEDUE + " TEXT,"
            + BH_COLUMN_DATERETURNED + " TEXT,"
            + "FOREIGN KEY (" + BH_COLUMN_BOOK_ID + ") "
            + "REFERENCES " + Book.TABLE_NAME + "(" + Book.COLUMN_ID + "));";

    public static final String SQL_CREATE_TABLE_BOOK = "CREATE TABLE " + Book.TABLE_NAME + " ("
            + Book.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + Book.COLUMN_TITLE + " TEXT NOT NULL,"
            + Book.COLUMN_AUTHOR + " TEXT NOT NULL,"
            + Book.COLUMN_GENRE + " TEXT NOT NULL,"
            + Book.COLUMN_PUBLISHER + " TEXT NOT NULL,"
            + Book.COLUMN_DATEPUBLISHED + " TEXT NOT NULL,"
            + Book.COLUMN_STATUS + " TEXT NOT NULL,"
            + Book.COLUMN_IMAGE + " BLOB NOT NULL"
            + ");";

    public DatabaseHelper(Context context) {
        super(context, SCHEMA, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_BOOK);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_BORROW_HISTORY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        Log.i(TAG, "Upgrading the database from version " + i + " to " + i1);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Book.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_BORROW_HISTORY);

        onCreate(sqLiteDatabase);

    }

    public long addBook(Book book) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Book.COLUMN_TITLE, book.getTitle());
        contentValues.put(Book.COLUMN_AUTHOR, book.getAuthor());
        contentValues.put(Book.COLUMN_GENRE, book.getGenre());
        contentValues.put(Book.COLUMN_PUBLISHER, book.getPublisher());
        contentValues.put(Book.COLUMN_DATEPUBLISHED, sdf.format(book.getPublishedDate()));
        contentValues.put(Book.COLUMN_IMAGE, book.getImage());
        contentValues.put(Book.COLUMN_STATUS, book.getStatus());

        long id = db.insert(Book.TABLE_NAME, null, contentValues);

        db.close();

        return id;

    }

//    public Cursor getAllBooksCursor() {
//        SQLiteDatabase db = getReadableDatabase();
//        String query = "SELECT * FROM " + Book.TABLE_NAME + " t JOIN " + TABLE_NAME_BORROW_HISTORY + " bh ON t." + Book.COLUMN_ID + " = bh." + BH_COLUMN_BOOK_ID;
//        return db.rawQuery(query, null);
//    }

    public Book viewBook(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(Book.TABLE_NAME,
                null,
                Book.COLUMN_ID + " =?",
                new String[]{id + ""},
                null,
                null,
                null);

        Book book = null;
        if (c.moveToFirst()) {

            int bookId = c.getInt(c.getColumnIndex(Book.COLUMN_ID));
            String title = c.getString(c.getColumnIndex(Book.COLUMN_TITLE));
            String author = c.getString(c.getColumnIndex(Book.COLUMN_AUTHOR));
            String genre = c.getString(c.getColumnIndex(Book.COLUMN_GENRE));
            String publisher = c.getString(c.getColumnIndex(Book.COLUMN_PUBLISHER));
            String status = c.getString(c.getColumnIndex(Book.COLUMN_STATUS));

            byte[] image = c.getBlob(c.getColumnIndex(Book.COLUMN_IMAGE));


            String datePublished = c.getString(c.getColumnIndex(Book.COLUMN_DATEPUBLISHED));

            book = new Book();
            book.setId(bookId);
            book.setTitle(title);
            book.setAuthor(author);
            book.setGenre(genre);
            book.setPublisher(publisher);

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = new Date();
            try {
                startDate = df.parse(datePublished);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            book.setPublishedDate(startDate);


            book.setImage(image);
            book.setStatus(status);
        }
        c.close();
        db.close();
        return book;
    }

    public Cursor getAllBooksCursor() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(Book.TABLE_NAME, null, null, null, null, null, null, null);
    }
}
