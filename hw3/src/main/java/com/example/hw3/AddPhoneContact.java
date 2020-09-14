package com.example.hw3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddPhoneContact extends AppCompatActivity {
    private EditText itemName;
    private EditText itemPhoneNumber;
    private ImageView arrowBack;
    private Button addButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone_number);

        itemName = findViewById(R.id.item_name);
        itemPhoneNumber = findViewById(R.id.item_phone_number);
        arrowBack = findViewById(R.id.back_arrow);
        addButton = findViewById(R.id.button_add);

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!itemName.getText().toString().equals("") && !itemPhoneNumber.getText().toString().equals("")) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("ADD_NAME", itemName.getText().toString());
                    resultIntent.putExtra("ADD_PHONE_NUMBER", itemPhoneNumber.getText().toString());

                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                }
            }
        });
    }
}


