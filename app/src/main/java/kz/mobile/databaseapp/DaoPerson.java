package kz.mobile.databaseapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DaoPerson {

    @Insert
    void insert(Person2 person);

    @Update
    void update(Person2 person);

    @Query("SELECT * FROM persons WHERE id = :id")
    Person2 getPerson(int id);

    @Delete
    void deletePerson(Person2 person);

    @Query("SELECT * FROM persons")
    List<Person2> getAllPersons();

}
