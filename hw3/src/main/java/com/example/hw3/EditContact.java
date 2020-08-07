package com.example.hw3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditContact extends AppCompatActivity {
    private EditText line1;
    private EditText line2;
    private Button buttonUpdate;
    private Button buttonRemove;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        line1 = findViewById(R.id.name);
        line2 = findViewById(R.id.phone_number);
        buttonUpdate = findViewById(R.id.button_update);
        buttonRemove = findViewById(R.id.button_remove);

        final String lineOneToEdit = getIntent().getStringExtra("LINE_1");
        final String lineTwoToEdit = getIntent().getStringExtra("LINE_2");
        line1.setText(lineOneToEdit);
        line2.setText(lineTwoToEdit);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!line1.getText().toString().equals(lineOneToEdit)
                        && !line2.getText().toString().equals(lineTwoToEdit)) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("UPDATED_LINE_1", line1.getText().toString());
                    resultIntent.putExtra("UPDATED_LINE_2", line2.getText().toString());

                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                }
            }
        });

        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Что здесь написать?

                finish();
            }
        });
    }
}

