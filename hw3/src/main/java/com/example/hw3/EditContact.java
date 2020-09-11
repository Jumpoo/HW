package com.example.hw3;

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
    private EditText nameLine1;
    private EditText phoneLine2;
    private TextView phoneHeading;
    private EditText emailLine3;
    private TextView emailHeading;
    private ImageView arrowBack;
    private Button buttonUpdate;
    private Button buttonRemove;
    private Contact contact;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        nameLine1 = findViewById(R.id.name);
        phoneLine2 = findViewById(R.id.phone_number);
        phoneHeading = findViewById(R.id.phone_heading);
        emailLine3 = findViewById(R.id.email);
        emailHeading = findViewById(R.id.email_heading);
        arrowBack = findViewById(R.id.back_arrow);
        buttonUpdate = findViewById(R.id.button_update);
        buttonRemove = findViewById(R.id.button_remove);

        contact = (Contact) getIntent().getSerializableExtra("ITEM");

        nameLine1.setText(contact.getLine1());
        phoneLine2.setText(contact.getLine2());
        emailLine3.setText(contact.getLine2());

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        phoneLine2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!phoneLine2.getText().toString().equals(contact.getLine2())) {
                    emailLine3.setEnabled(false);
                    emailLine3.setFocusable(false);
                    emailLine3.setClickable(false);
                    emailLine3.setVisibility(View.INVISIBLE);
                    emailHeading.setVisibility(View.INVISIBLE);
                }
            }
        });

        emailLine3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!emailLine3.getText().toString().equals(contact.getLine2())) {
                    phoneLine2.setEnabled(false);
                    phoneLine2.setFocusable(false);
                    phoneLine2.setClickable(false);
                    phoneLine2.setVisibility(View.INVISIBLE);
                    phoneHeading.setVisibility(View.INVISIBLE);
                }
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!nameLine1.getText().toString().equals(contact.getLine2()) && !phoneLine2.getText().toString().equals(contact.getLine2())
                && emailLine3.getText().toString().equals(contact.getLine2())) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("UPDATED_PHONE_CONTACT",
                            new Contact(R.drawable.phone_icon, nameLine1.getText().toString(), phoneLine2.getText().toString()));
                    resultIntent.putExtra("ITEM FOR REMOVE", contact);

                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();

                }  else if (!nameLine1.getText().toString().equals(contact.getLine2()) && !emailLine3.getText().toString().equals(contact.getLine2())
                        && phoneLine2.getText().toString().equals(contact.getLine2())) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("UPDATED_EMAIL_CONTACT",
                            new Contact(R.drawable.email_icon, nameLine1.getText().toString(), emailLine3.getText().toString()));
                    resultIntent.putExtra("ITEM FOR REMOVE", contact);

                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                }
            }
        });

        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                contact = (Contact) getIntent().getSerializableExtra("ITEM");
                resultIntent.putExtra("ITEM FOR REMOVE", contact);

                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}

