package kz.mobile.databaseapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Person2.class}, version = 2)
public abstract class PersonRoomDatabase extends RoomDatabase  {

    public abstract DaoPerson daoPerson();

    private static final String PERSON_DATABASE = "person_database";
    private static PersonRoomDatabase instance;

    static PersonRoomDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context,
                    PersonRoomDatabase.class,
                    PERSON_DATABASE
            ).build();
        }
        return instance;
    }



}
