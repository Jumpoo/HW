package com.example.hw5

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_email.*

class AddEmailContact : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_email)

        viewBackArrow.setOnClickListener { finish() }

        viewButtonAdd.setOnClickListener {

            if (viewItemName.text.toString() != "" && viewItemEmail.text.toString() != "") {
                val resultIntent = Intent()
                resultIntent.putExtra(ADD_NAME_E, viewItemName.text.toString())
                resultIntent.putExtra(ADD_EMAIL, viewItemEmail.text.toString())
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }

    companion object {
        const val ADD_NAME_E = "ADD_NAME_E"
        const val ADD_EMAIL = "ADD_EMAIL"
    }
}