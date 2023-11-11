package com.example.lab3;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.

    // Below variable is for our database name.
    private static final String DB_NAME = "notedb";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "mynotes";

    // below variable is for our id column.
    private static final String ID_COL = "id";

    // below variable is for our note name / title column
    private static final String TITLE_COL = "title";

    // below variable id for our note description column.
    private static final String DESCRIPTION_COL = "description";

    // below variable for our note color column.
    private static final String COLOR_COL = "color";


    // creating a constructor for our database handler.
    public DBHandler(Context context) {

        super(context, DB_NAME, null, DB_VERSION);

    }

    // below method is for creating a database by running a sqlite query
    @Override

    public void onCreate(SQLiteDatabase db) {

        // on below line we are creating an sqlite query and we are setting our column names along with their data types.
        String query = "CREATE TABLE " + TABLE_NAME + " ("

                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "

                + TITLE_COL + " TEXT,"

                + DESCRIPTION_COL + " TEXT,"

                + COLOR_COL + " TEXT)";


        // at last we are calling a exec sql method to execute above sql query
        db.execSQL(query);

    }

    // this method is use to add new  Notes to our sqlite database.
    public void addNewNote(String noteName, String noteDescription, String noteColor) {

        // on below line we are creating a variable for our sqlite database and calling writable method as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a variable for content values.
        ContentValues values = new ContentValues();


        // on below line we are passing all values along with its key and value pair.
        values.put(TITLE_COL, noteName);
        values.put(DESCRIPTION_COL, noteDescription);
        values.put(COLOR_COL, noteColor);


        // after adding all values we are passing content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our database after adding database.
        db.close();

    }

    // we have created a new method for reading all the notes.
    public ArrayList<NoteModal> readNotes()
    {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorNotes = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        // on below line we are creating a new array list.
        ArrayList<NoteModal> noteModalArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorNotes.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                noteModalArrayList.add(new NoteModal(
                        cursorNotes.getString(1),
                        cursorNotes.getString(2),
                        cursorNotes.getString(3)));
            } while (cursorNotes.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor and returning our array list.
        cursorNotes.close();
        return noteModalArrayList;
    }

    // below is the method for updating our notes
    public void updateNote(String originalNoteName, String noteName, String noteDescription, String noteColor) {

        // calling a method to get writable database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // on below line we are passing all values along with its key and value pair.
        values.put(TITLE_COL, noteName);
        values.put(DESCRIPTION_COL, noteDescription);
        values.put(COLOR_COL, noteColor);

        // on below line we are calling a update method to update our database and passing our values.
        // and we are comparing it with name of our note which is stored in original name variable.
        db.update(TABLE_NAME, values, TITLE_COL + " = ?", new String[]{originalNoteName});
        db.close();
    }

    // below is the method for deleting our note.
    public void deleteNote(String noteName) {

        // on below line we are creating a variable to write our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are calling a method to delete our note and we are comparing it with our note name.
        db.delete(TABLE_NAME, "title=?", new String[]{noteName});
        db.close();
    }
    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
}