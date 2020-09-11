package com.example.hw5

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContactDao {
    @Insert
    fun insert(contact: Contact)

    @Update
    fun update(contact: Contact)

    @Delete
    fun delete(contact: Contact)

    @Query("SELECT * FROM ContactTable")
    fun getAllContacts(): LiveData<List<Contact>>
}