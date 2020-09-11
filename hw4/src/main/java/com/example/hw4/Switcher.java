package com.example.hw4;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Switcher extends AppCompatActivity {
    public static final String SNACKBAR = "SNACKBAR";
    public static final String TOAST = "TOAST";

    private RadioButton radioButtonSnackbarOn;
    private RadioButton radioButtonToastOn;
    private RadioGroup radioGroup;
    private Button buttonSave;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private SharedPreferences snackbarPreference;
    private SharedPreferences toastPreference;

    public static Intent intentForSwitcher(Context context) {
        return new Intent(context, Switcher.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switcher);

        radioButtonSnackbarOn = findViewById(R.id.view_radio_button_snackbar_on);
        radioButtonToastOn = findViewById(R.id.view_radio_button_toast_on);
        radioGroup = findViewById(R.id.view_radio_group);
        buttonSave = findViewById(R.id.view_button_save);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getId() == R.id.view_radio_button_snackbar_on) {
                    saveSnackbarPreference();
                } else {
                    saveToastPreference();
                }
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                if (radioButtonSnackbarOn.isChecked()) {
                    resultIntent.putExtra(SNACKBAR, loadSnackbarPreference());
                    setResult(Activity.RESULT_OK, resultIntent);
                } else if (radioButtonToastOn.isChecked()) {
                    resultIntent.putExtra(TOAST, loadToastPreference());
                    setResult(Activity.RESULT_OK, resultIntent);
                }
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveSnackbarPreference() {
        snackbarPreference = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = snackbarPreference.edit();
        editor.putString(SNACKBAR, "");
        editor.apply();
    }

    private void saveToastPreference() {
        toastPreference = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = toastPreference.edit();
        editor.putString(TOAST, "");
        editor.apply();
    }

    private String loadSnackbarPreference() {
        snackbarPreference = getPreferences(Context.MODE_PRIVATE);
        return snackbarPreference.getString(SNACKBAR, null);
    }

    private String loadToastPreference() {
        toastPreference = getPreferences(Context.MODE_PRIVATE);
        return toastPreference.getString(TOAST, null);
    }
}
