package kz.mobile.databaseapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database_app";
    private static final int VERSION = 1;
    private static final String PERSON_TABLE = "PERSON_TABLE";

    private static final String PERSON_ID = "id";
    private static final String PERSON_NAME = "name";
    private static final String PERSON_SURNAME = "surname";
    private static final String PERSON_AGE = "age";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createPersonTable = "CREATE TABLE " +
                PERSON_TABLE +
                "(" +
                    PERSON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT 1, " +
                    PERSON_NAME + " TEXT, " +
                    PERSON_SURNAME + " TEXT, " +
                    PERSON_AGE + " INTEGER" +
                ")";
        db.execSQL(createPersonTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PERSON_TABLE);
        onCreate(db);
    }

    public void insert(Person person) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(PERSON_NAME, person.getName());
        values.put(PERSON_SURNAME, person.getSurname());
        values.put(PERSON_AGE, person.getAge());

        database.insert(PERSON_TABLE, null, values);
        database.close();
    }

    public Person getPerson(int id) {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(
                PERSON_TABLE,
                new String[]{ PERSON_ID, PERSON_NAME, PERSON_SURNAME, PERSON_AGE },
                PERSON_ID + " =? ",
                new String[]{String.valueOf(id) },
                null,
                null,
                null,
                null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Person person = new Person(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                Integer.parseInt(cursor.getString(3))
        );
        return person;
    }

    public void update(Person person) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PERSON_NAME, person.getName());
        values.put(PERSON_SURNAME, person.getSurname());
        values.put(PERSON_AGE, person.getAge());

        database.update(PERSON_TABLE,
                values,
                PERSON_ID + " =? ",
                new String[]{ String.valueOf(person.getId()) }
                );
        database.close();
    }

    public void delete(int id) {
        SQLiteDatabase database = getWritableDatabase();

        database.delete(PERSON_TABLE, PERSON_ID + " =? ",
                new String[]{ String.valueOf(id) });
        database.close();
    }

    public List<Person> getAllPersons() {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + PERSON_TABLE, null);
        List<Person> persons = new ArrayList<>();
        while (cursor.moveToNext()) {
            Person person = new Person(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    Integer.parseInt(cursor.getString(3))
            );
            persons.add(person);
        }
        return persons;
    }
}
