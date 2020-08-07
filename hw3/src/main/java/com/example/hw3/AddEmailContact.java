package com.example.hw3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddEmailContact extends AppCompatActivity {
    private EditText itemName;
    private EditText itemEmail;
    private RadioButton phoneNumberRadioButton;
    private Button addButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_email);

        itemName = findViewById(R.id.item_name);
        itemEmail = findViewById(R.id.item_email);
        phoneNumberRadioButton = findViewById(R.id.phone_number_button);
        addButton = findViewById(R.id.button_add);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("ADD_NAME_E", itemName.getText().toString());
                resultIntent.putExtra("ADD_EMAIL", itemEmail.getText().toString());

                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });

        phoneNumberRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAddPhoneContact();
                finish();
            }
        });
    }

    private void startAddPhoneContact() {
        Intent intent = new Intent(this, AddPhoneContact.class);
        startActivity(intent);
    }
}
