package com.example.hw90;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditContact extends AppCompatActivity {
    public static final String UPDATED_PHONE_CONTACT = "UPDATED_PHONE_CONTACT";
    public static final String UPDATED_EMAIL_CONTACT ="UPDATED_EMAIL_CONTACT";
    public static final String ITEM_FOR_UPDATE_OR_REMOVE ="ITEM FOR UPDATE OR REMOVE";

    private EditText name;
    private EditText phone;
    private TextView phoneHeading;
    private EditText email;
    private TextView emailHeading;
    private ImageView arrowBack;
    private Button buttonUpdate;
    private Button buttonRemove;

    private Contact contact;
    private int contactId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone_number);
        phoneHeading = findViewById(R.id.phone_heading);
        email = findViewById(R.id.email);
        emailHeading = findViewById(R.id.email_heading);
        arrowBack = findViewById(R.id.back_arrow);
        buttonUpdate = findViewById(R.id.button_update);
        buttonRemove = findViewById(R.id.button_remove);

        contact = (Contact) getIntent().getSerializableExtra(MainActivity.ITEM);
        contactId = contact.getId();

        name.setText(contact.getLine1());
        phone.setText(contact.getLine2());
        email.setText(contact.getLine2());

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!phone.getText().toString().equals(contact.getLine2())) {
                    email.setEnabled(false);
                    email.setFocusable(false);
                    email.setClickable(false);
                    email.setVisibility(View.INVISIBLE);
                    emailHeading.setVisibility(View.INVISIBLE);
                }
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!email.getText().toString().equals(contact.getLine2())) {
                    phone.setEnabled(false);
                    phone.setFocusable(false);
                    phone.setClickable(false);
                    phone.setVisibility(View.INVISIBLE);
                    phoneHeading.setVisibility(View.INVISIBLE);
                }
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!name.getText().toString().equals(contact.getLine2()) && !phone.getText().toString().equals(contact.getLine2())
                && email.getText().toString().equals(contact.getLine2())) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(UPDATED_PHONE_CONTACT,
                            new Contact(R.drawable.phone_icon, name.getText().toString(), phone.getText().toString()));
                    resultIntent.putExtra(ITEM_FOR_UPDATE_OR_REMOVE, contact);
                    resultIntent.putExtra(MainActivity.CONTACT_ID, contactId);

                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();

                }  else if (!name.getText().toString().equals(contact.getLine2()) && !email.getText().toString().equals(contact.getLine2())
                        && phone.getText().toString().equals(contact.getLine2())) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(UPDATED_EMAIL_CONTACT,
                            new Contact(R.drawable.email_icon, name.getText().toString(), email.getText().toString()));
                    resultIntent.putExtra(ITEM_FOR_UPDATE_OR_REMOVE, contact);
                    resultIntent.putExtra(MainActivity.CONTACT_ID, contactId);

                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                }
            }
        });

        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                contact = (Contact) getIntent().getSerializableExtra(MainActivity.ITEM);
                resultIntent.putExtra(ITEM_FOR_UPDATE_OR_REMOVE, contact);

                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}

