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

public class Preference extends AppCompatActivity {
    public static final String SNACKBAR = "SNACKBAR";
    public static final String TOAST = "TOAST";

    private RadioButton radioButtonSnackbarOn;
    private RadioButton radioButtonToastOn;
    private RadioGroup radioGroup;
    private Button buttonSave;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private SharedPreferences preference;

    public static Intent intentForPreferenceActivity(Context context) {
        return new Intent(context, Preference.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

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
                savePreference();
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

    private void savePreference() {
        preference = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString(SNACKBAR, "");
        editor.putString(TOAST, "");
        editor.apply();
    }

    private String loadSnackbarPreference() {
        preference = getPreferences(Context.MODE_PRIVATE);
        return preference.getString(SNACKBAR, null);
    }

    private String loadToastPreference() {
        preference = getPreferences(Context.MODE_PRIVATE);
        return preference.getString(TOAST, null);
    }
}
