package com.example.hw90;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDao {

    @Insert
    void insert(Contact contact);

    @Update
    void update(Contact contact);

    @Delete
    void delete(Contact contact);

    @Query("SELECT * FROM ContactTable")
    LiveData<List<Contact>> getAllContacts();

    @Query("SELECT * FROM ContactTable WHERE line1 LIKE :filterQuery")
    List<Contact> getFilteredContact(String filterQuery);
}
