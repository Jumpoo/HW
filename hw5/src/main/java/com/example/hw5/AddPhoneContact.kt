package com.example.hw5

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_phone_number.*

class AddPhoneContact : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_phone_number)

        viewBackArrow.setOnClickListener { finish() }

        viewButtonAdd.setOnClickListener {

            if (viewItemName.text.toString() != "" && viewItemPhoneNumber.text.toString() != "") {
                val resultIntent = Intent()
                resultIntent.putExtra(ADD_NAME, viewItemName.text.toString())
                resultIntent.putExtra(ADD_PHONE_NUMBER, viewItemPhoneNumber.text.toString())
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }

    companion object {
        const val ADD_NAME = "ADD_NAME"
        const val ADD_PHONE_NUMBER = "ADD_PHONE_NUMBER"
    }
}