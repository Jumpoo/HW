package com.example.hw5

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_edit_contact.*


class EditContact : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contact)

        val contact = intent.getSerializableExtra(MainActivity.ITEM) as Contact
        val contactId = contact.id

        viewName.setText(contact.line1)
        viewPhoneNumber.setText(contact.line2)
        viewEmail.setText(contact.line2)

        viewBackArrow.setOnClickListener { finish() }

        viewPhoneNumber.setOnClickListener {
            if (viewPhoneNumber.text.toString() != contact.line2) {
                viewEmail.isEnabled = false
                viewEmail.isFocusable = false
                viewEmail.isClickable = false
                viewEmail.visibility = View.INVISIBLE
                viewEmailHeading.visibility = View.INVISIBLE
            }
        }

        viewEmail.setOnClickListener {
            if (viewEmail.text.toString() != contact.line2) {
                viewPhoneNumber.isEnabled = false
                viewPhoneNumber.isFocusable = false
                viewPhoneNumber.isClickable = false
                viewPhoneNumber.visibility = View.INVISIBLE
                viewPhoneHeading.visibility = View.INVISIBLE
            }
        }

        viewButtonUpdate.setOnClickListener {
            if (viewName.text.toString() != contact.line2 && viewPhoneNumber.text.toString() != contact.line2
                    && viewEmail.text.toString() == contact.line2) {

                val resultIntent = Intent()
                resultIntent.putExtra(UPDATED_PHONE_CONTACT,
                        Contact(R.drawable.phone_icon, viewName.text.toString(), viewPhoneNumber.text.toString()))
                resultIntent.putExtra(ITEM_FOR_UPDATE_OR_REMOVE, contact)
                resultIntent.putExtra(MainActivity.CONTACT_ID, contactId)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()

            } else if (viewName.text.toString() != contact.line2 && viewEmail.text.toString() != contact.line2
                    && viewPhoneNumber.text.toString() == contact.line2) {

                val resultIntent = Intent()
                resultIntent.putExtra(UPDATED_EMAIL_CONTACT,
                        Contact(R.drawable.email_icon, viewName.text.toString(), viewEmail.text.toString()))
                resultIntent.putExtra(ITEM_FOR_UPDATE_OR_REMOVE, contact)
                resultIntent.putExtra(MainActivity.CONTACT_ID, contactId)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }

        viewButtonRemove.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra(ITEM_FOR_UPDATE_OR_REMOVE, contact)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    companion object {
        const val UPDATED_PHONE_CONTACT = "UPDATED_PHONE_CONTACT"
        const val UPDATED_EMAIL_CONTACT = "UPDATED_EMAIL_CONTACT"
        const val ITEM_FOR_UPDATE_OR_REMOVE = "ITEM FOR UPDATE OR REMOVE"
    }
}

