package com.example.hw90;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Contact.class, version = 1)
public abstract class ContactDatabase extends RoomDatabase {

    private static ContactDatabase instance;

    public abstract ContactDao contactDao();

    public static synchronized ContactDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ContactDatabase.class, "contactDatabase")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ContactDao contactDao;

        private PopulateDbAsyncTask(ContactDatabase db) {
            contactDao = db.contactDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            contactDao.insert(new Contact(R.drawable.phone_icon, "Tom", "+484845949"));
            contactDao.insert(new Contact(R.drawable.email_icon, "Jack", "+12345949"));
            contactDao.insert(new Contact(R.drawable.phone_icon, "Sam", "900045949"));
            contactDao.insert(new Contact(R.drawable.email_icon, "Andrew", "77777745949"));
            return null;
        }
    }
}
