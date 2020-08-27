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

//               Такие формулы радиуса окружности не работали:
//
//               if ( (Math.pow((x - customView.getCenterOfWidth()), 2)) +
//               (Math.pow((y - customView.getCenterOfHeight()), 2)) ==
//                        Math.pow(customView.getRadiusForBigCircle(), 2) )

//               if ( (Math.pow(customView.getRadiusForBigCircle(), 2) == (Math.pow(x, 2)) - 2 * x * customView.getCenterOfWidth() +
//                        (Math.pow(customView.getCenterOfWidth(), 2)) + (Math.pow(y, 2)) - 2 * y * customView.getCenterOfHeight() +
//                        (Math.pow(customView.getCenterOfHeight(), 2))) )

                if (x >= customView.getCenterOfWidth() + customView.getRadiusForSmallCircle() &&
                        y >= customView.getCenterOfHeight() + customView.getRadiusForSmallCircle() &&
                        x <= customView.getCenterOfWidth() + customView.getRadiusForBigCircle() &&
                        y <= customView.getCenterOfHeight() + customView.getRadiusForBigCircle()) {
                    customView.touchYellowArc();
                } else if (x <= customView.getCenterOfWidth() - customView.getRadiusForSmallCircle() &&
                        y >= customView.getCenterOfHeight() + customView.getRadiusForSmallCircle() &&
                        x >= customView.getCenterOfWidth() - customView.getRadiusForBigCircle() &&
                        y <= customView.getCenterOfHeight() + customView.getRadiusForBigCircle()) {
                    customView.touchBlueArc();
                } else if (x <= customView.getCenterOfWidth() - customView.getRadiusForSmallCircle() &&
                        y <= customView.getCenterOfHeight() + customView.getRadiusForSmallCircle() &&
                        x >= customView.getCenterOfWidth() - customView.getRadiusForBigCircle() &&
                        y >= customView.getCenterOfHeight() - customView.getRadiusForBigCircle()) {
                    customView.touchRedArc();
                } else if (x >= customView.getCenterOfWidth() + customView.getRadiusForSmallCircle() &&
                        y <= customView.getCenterOfHeight() - customView.getRadiusForSmallCircle() &&
                        x <= customView.getCenterOfWidth() + customView.getRadiusForBigCircle() &&
                        y >= customView.getCenterOfHeight() - customView.getRadiusForBigCircle()) {
                    customView.touchGreenArc();
                } else if (x <= customView.getCenterOfWidth() + customView.getRadiusForSmallCircle() &&
                        x >= customView.getCenterOfWidth() - customView.getRadiusForSmallCircle() &&
                        y >= customView.getCenterOfHeight() - customView.getRadiusForSmallCircle() &&
                        y <= customView.getCenterOfHeight() + customView.getRadiusForSmallCircle()) {
                    customView.touchPurpleSmallCircle();
                }
            }
        });
    }

    private void startSwitcher() {
        startActivityForResult(Switcher.intentForSwitcher(this), 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == Activity.RESULT_OK && data != null) {
            checkSnackbar = data.getStringExtra(Switcher.SNACKBAR);
            checkToast = data.getStringExtra(Switcher.TOAST);
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
        if (itemIdInMenu == R.id.switcher) {
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
                startSwitcher();
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
