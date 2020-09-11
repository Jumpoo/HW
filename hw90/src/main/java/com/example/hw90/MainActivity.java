package com.example.hw90;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity  {
    public static final String CONTACT_ID = "CONTACT ID";
    public static final String ITEM = "ITEM";
    public static final int ADD_PHONE_CONTACT = 1;
    public static final int ADD_EMAIL_CONTACT = 2;
    public static final int EDIT_CONTACT = 3;

    private ContactViewModel contactViewModel;

    private FloatingActionButton add;
    private FloatingActionButton addPhoneNumber;
    private FloatingActionButton addEmail;
    private boolean clicked = false;

    private EditText search;
    private List <Contact> mItems = new ArrayList<>();
    private ContactListAdapter mAdapter = new ContactListAdapter();
    private RecyclerView recyclerView;

    private Contact contact;
    private int contactId;
    private Contact contactForPhoneUpdate;
    private Contact contactForEmailUpdate;

    public String q = "Tom";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add = findViewById(R.id.add_contact_button);
        addPhoneNumber = findViewById(R.id.add_phone_button);
        addEmail = findViewById(R.id.add_email_button);
        search = findViewById(R.id.search);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        contactViewModel.getFilteredContact(q);
        contactViewModel.getAllContacts().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                mAdapter.setContacts(contacts);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateVisibility();
            }
        });

        addPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAddPhoneContact();
                updateVisibility();
            }
        });

        addEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAddEmailContact();
                updateVisibility();
            }
        });

        mAdapter.setMListener(new ContactListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Contact contact) {
                Intent intent = new Intent(MainActivity.this, EditContact.class);
                intent.putExtra(ITEM, contact);
                startActivityForResult(intent, EDIT_CONTACT);
            }
        });

        //        Search start ----------------------------------------------------------------------------------
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                q = s.toString();
                contactViewModel.getFilteredContact(q);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void updateVisibility() {
        clicker(clicked);
        clicked = !clicked;
    }

    private void clicker(boolean clicked) {
        if (!clicked) {
            addPhoneNumber.setVisibility(View.VISIBLE);
            addEmail.setVisibility(View.VISIBLE);
        } else {
            addPhoneNumber.setVisibility(View.INVISIBLE);
            addEmail.setVisibility(View.INVISIBLE);
        }
    }

    private void startAddPhoneContact() {
        Intent intent = new Intent(this, AddPhoneContact.class);
        startActivityForResult(intent, ADD_PHONE_CONTACT);
    }

    private void startAddEmailContact() {
        Intent intent = new Intent(this, AddEmailContact.class);
        startActivityForResult(intent, ADD_EMAIL_CONTACT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_PHONE_CONTACT && resultCode == Activity.RESULT_OK && data != null) {
            String addedName = data.getStringExtra(AddPhoneContact.ADD_NAME);
            String addedPhoneNumber = data.getStringExtra(AddPhoneContact.ADD_PHONE_NUMBER);
           contactViewModel.insert(new Contact(R.drawable.phone_icon, addedName, addedPhoneNumber));

        } else if (requestCode == ADD_EMAIL_CONTACT && resultCode == Activity.RESULT_OK && data != null) {
            String addedName = data.getStringExtra(AddEmailContact.ADD_NAME_E);
            String addedEmail = data.getStringExtra(AddEmailContact.ADD_EMAIL);
            contactViewModel.insert(new Contact(R.drawable.email_icon, addedName, addedEmail));
        }

        if (requestCode == EDIT_CONTACT && resultCode == Activity.RESULT_OK && data != null) {
            contact = (Contact) data.getSerializableExtra(EditContact.ITEM_FOR_UPDATE_OR_REMOVE);
            contactId = data.getIntExtra(CONTACT_ID, 0);
            contactForPhoneUpdate = (Contact) data.getSerializableExtra(EditContact.UPDATED_PHONE_CONTACT);
            contactForEmailUpdate = (Contact) data.getSerializableExtra(EditContact.UPDATED_EMAIL_CONTACT);

            if (contact != null && contactForPhoneUpdate == null && contactForEmailUpdate == null) {
                contactViewModel.delete(contact);

            } else if (contact != null && contactForPhoneUpdate != null && contactForEmailUpdate == null) {
                contactForPhoneUpdate.setId(contactId);
                contactViewModel.update(contactForPhoneUpdate);

            } else if (contact != null && contactForPhoneUpdate == null && contactForEmailUpdate != null) {
                contactForEmailUpdate.setId(contactId);
                contactViewModel.update(contactForEmailUpdate);
            }
        }
    }
}
