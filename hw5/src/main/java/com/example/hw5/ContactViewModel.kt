package com.example.hw5

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData


class ContactViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ContactRepository = ContactRepository(application)
    private val allContacts: LiveData<List<Contact>> = repository.getAllContacts()

    fun insert(contact: Contact) {
        repository.insert(contact)
    }

    fun update(contact: Contact) {
        repository.update(contact)
    }

    fun delete(contact: Contact) {
        repository.delete(contact)
    }

    fun getAllContacts(): LiveData<List<Contact>> {
        return allContacts
    }
}