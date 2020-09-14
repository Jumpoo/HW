package com.example.hw4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private CustomView customView;
    private Toolbar toolbar;
    private int itemIdInMenu;
    private AlertDialog alertDialog;
    private String checkSnackbar;
    private String checkToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customView = findViewById(R.id.custom_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        customView.setTouchActionListener(new CustomView.TouchActionListener() {
            @Override
            public void onTouchDown(int x, int y) {
                if (checkSnackbar != null) {
                    Snackbar.make(findViewById(R.id.main_view), "X: " + x + " Y: " + y, Snackbar.LENGTH_LONG).show();
                } else if (checkToast != null) {
                    Toast.makeText(MainActivity.this, "X: " + x + " Y: " + y, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void startPreferenceActivity() {
        startActivityForResult(Preference.intentForPreferenceActivity(this), 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == Activity.RESULT_OK && data != null) {
            checkSnackbar = data.getStringExtra(Preference.SNACKBAR);
            checkToast = data.getStringExtra(Preference.TOAST);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       itemIdInMenu = item.getItemId();
        if (itemIdInMenu == R.id.choose_preference) {
            showConfirmationDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showConfirmationDialog() {
        alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.alert_dialog_title)
                .setMessage(R.string.alert_dialog_message)
                .setPositiveButton((R.string.alert_dialog_positive_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startPreferenceActivity();
            }
                })
                .setNegativeButton((R.string.alert_dialog_negative_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.closeOptionsMenu();
                    }
                })
                .create();
        alertDialog.show();
    }
}
