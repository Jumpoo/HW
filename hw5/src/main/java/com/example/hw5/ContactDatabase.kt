package com.example.hw5

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase: RoomDatabase() {

    abstract val contactDao: ContactDao

    companion object {
        @Volatile
        private var instance: ContactDatabase? = null
        fun getInstance(context: Context): ContactDatabase {
            synchronized(this) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                            ContactDatabase::class.java, "contactDatabase")
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallBack)
                            .build()
                }
                return instance!!
            }
        }

        private val roomCallBack: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance).execute()
            }
        }
    }

    private class PopulateDbAsyncTask(db: ContactDatabase?) : AsyncTask<Void?, Void?, Void?>() {
        private var contactDao: ContactDao = db!!.contactDao

        override fun doInBackground(vararg p0: Void?): Void? {
            contactDao.insert(Contact(R.drawable.phone_icon, "Tom", "+484845949"))
            contactDao.insert(Contact(R.drawable.email_icon, "Jack", "+12345949"))
            contactDao.insert(Contact(R.drawable.phone_icon, "Sam", "900045949"))
            contactDao.insert(Contact(R.drawable.email_icon, "Andrew", "77777745949"))
            return null
        }
    }
}

