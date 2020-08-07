package com.example.hw3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements ContactListAdapter.OnItemClickListener {
    private FloatingActionButton add;
    private FloatingActionButton addPhoneNumber;
    private FloatingActionButton addEmail;
    private boolean clicked = false;

    private TextView line1;
    private  TextView line2;
    private EditText search;
    private List <Contact> mItems = new ArrayList<>();
    private ContactListAdapter mAdapter = new ContactListAdapter(mItems, this);

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add = findViewById(R.id.add_contact_button);
        addPhoneNumber = findViewById(R.id.add_phone_button);
        addEmail = findViewById(R.id.add_email_button);
        line1 = findViewById(R.id.item_line_1);
        line2 = findViewById(R.id.item_line_2);
        search = findViewById(R.id.search);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

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

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void filter(String text) {
        ArrayList<Contact> filteredList = new ArrayList<>();
        for (Contact item : mItems) {
            if (item.getLine1().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        mAdapter.filterList(filteredList);
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
        startActivityForResult(intent, 10);
    }

    private void startAddEmailContact() {
        Intent intent = new Intent(this, AddEmailContact.class);
        startActivityForResult(intent, 20);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, EditContact.class);
        intent.putExtra("POSITION", mItems.get(position));
//        intent.putExtra("LINE_1", line1.getText().toString());  Из-за этих строк закрывается приложение, если нажать на контакт в списке
//        intent.putExtra("LINE_2", line2.getText().toString());  Из-за этих строк закрывается приложение, если нажать на контакт в списке
        startActivityForResult(intent, 30);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10 && resultCode == Activity.RESULT_OK && data != null) {
            String addedName = data.getStringExtra("ADD_NAME");
            String addedPhoneNumber = data.getStringExtra("ADD_PHONE_NUMBER");

            ContactListAdapter adapter = (ContactListAdapter) recyclerView.getAdapter();
            adapter.addItem(new Contact(R.drawable.phone_icon, addedName, addedPhoneNumber));

        } else if (requestCode == 20 && resultCode == Activity.RESULT_OK && data != null) {
            String addedName = data.getStringExtra("ADD_NAME_E");
            String addedEmail = data.getStringExtra("ADD_EMAIL");

            ContactListAdapter adapter = (ContactListAdapter) recyclerView.getAdapter();
            adapter.addItem(new Contact(R.drawable.email_icon, addedName, addedEmail));

        } else if (requestCode == 30 && resultCode == Activity.RESULT_OK && data != null) {
            String updatedLine1 = data.getStringExtra("UPDATED_LINE_1");
            String updatedLine2 = data.getStringExtra("UPDATED_LINE_2");

            ContactListAdapter adapter = (ContactListAdapter) recyclerView.getAdapter();
            adapter.addItem(new Contact(R.drawable.phone_icon, updatedLine1, updatedLine2));
        }
    }
}
