package com.example.hw5

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class ContactRepository(application: Application) {
    private var contactDao: ContactDao
    private var allContacts: LiveData<List<Contact>>

    init {
        val database: ContactDatabase = ContactDatabase.getInstance(
                application.applicationContext
        )
        contactDao = database.contactDao
        allContacts = contactDao.getAllContacts()
    }

    fun insert(contact: Contact) {
        InsertContactAsyncTask(contactDao).execute(contact)
    }

    fun update(contact: Contact) {
        UpdateContactAsyncTask(contactDao).execute(contact)
    }

    fun delete(contact: Contact) {
        DeleteContactAsyncTask(contactDao).execute(contact)
    }

    fun getAllContacts(): LiveData<List<Contact>> {
        return allContacts
    }


    private class InsertContactAsyncTask(contactDao: ContactDao) : AsyncTask<Contact, Void?, Void?>() {
        private var contactDao: ContactDao = contactDao

        override fun doInBackground(vararg p0: Contact): Void? {
            contactDao.insert(p0[0])
            return null
        }
    }

    private class UpdateContactAsyncTask(contactDao: ContactDao) : AsyncTask<Contact, Void?, Void?>() {
        private var contactDao: ContactDao = contactDao

        override fun doInBackground(vararg p0: Contact): Void? {
            contactDao.update(p0[0])
            return null
        }
    }

    private class DeleteContactAsyncTask(contactDao: ContactDao) : AsyncTask<Contact, Void?, Void?>() {
        private var contactDao: ContactDao = contactDao

        override fun doInBackground(vararg p0: Contact): Void? {
            contactDao.delete(p0[0])
            return null
        }
    }
}