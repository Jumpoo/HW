package com.example.hw5

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var contactViewModel: ContactViewModel? = null
    private var clicked = false
    private val mAdapter: ContactListAdapter = ContactListAdapter()
    private var contact: Contact? = null
    private var contactId = 0
    private var contactForPhoneUpdate: Contact? = null
    private var contactForEmailUpdate: Contact? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewRecyclerView.adapter = mAdapter
        viewRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        contactViewModel = ViewModelProvider(this).get<ContactViewModel>(ContactViewModel::class.java)
        contactViewModel!!.getAllContacts().observe(this, Observer<List<Contact>> { t -> mAdapter.setContacts(t) })

        viewAddContactButton.setOnClickListener { updateVisibility() }

        viewAddPhoneButton.setOnClickListener {
            startAddPhoneContact()
            updateVisibility()
        }

        viewAddEmailButton.setOnClickListener {
            startAddEmailContact()
            updateVisibility()
        }

        mAdapter.setMListener(object : ContactListAdapter.OnItemClickListener {
            override fun onItemClick(contact: Contact) {
                val intent = Intent(this@MainActivity, EditContact::class.java)
                intent.putExtra(ITEM, contact)
                startActivityForResult(intent, EDIT_CONTACT)
            }
        })
    }

    private fun updateVisibility() {
        clicker(clicked)
        clicked = !clicked
    }

    private fun clicker(clicked: Boolean) {
        if (!clicked) {
            viewAddPhoneButton.visibility = View.VISIBLE
            viewAddEmailButton.visibility = View.VISIBLE

        } else {
            viewAddPhoneButton.visibility = View.INVISIBLE
            viewAddEmailButton.visibility = View.INVISIBLE
        }
    }

    private fun startAddPhoneContact() {
        val intent = Intent(this, AddPhoneContact::class.java)
        startActivityForResult(intent, ADD_PHONE_CONTACT)
    }

    private fun startAddEmailContact() {
        val intent = Intent(this, AddEmailContact::class.java)
        startActivityForResult(intent, ADD_EMAIL_CONTACT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_PHONE_CONTACT && resultCode == Activity.RESULT_OK && data != null) {
            val addedName = data.getStringExtra(AddPhoneContact.ADD_NAME)
            val addedPhoneNumber = data.getStringExtra(AddPhoneContact.ADD_PHONE_NUMBER)
            contactViewModel!!.insert(Contact(R.drawable.phone_icon, addedName!!, addedPhoneNumber!!))

        } else if (requestCode == ADD_EMAIL_CONTACT && resultCode == Activity.RESULT_OK && data != null) {
            val addedName = data.getStringExtra(AddEmailContact.ADD_NAME_E)
            val addedEmail = data.getStringExtra(AddEmailContact.ADD_EMAIL)
            contactViewModel!!.insert(Contact(R.drawable.email_icon, addedName!!, addedEmail!!))
        }

        if (requestCode == EDIT_CONTACT && resultCode == Activity.RESULT_OK && data != null) {
            contact = data.getSerializableExtra(EditContact.ITEM_FOR_UPDATE_OR_REMOVE) as Contact?
            contactId = data.getIntExtra(CONTACT_ID, 0)
            contactForPhoneUpdate = data.getSerializableExtra(EditContact.UPDATED_PHONE_CONTACT) as Contact?
            contactForEmailUpdate = data.getSerializableExtra(EditContact.UPDATED_EMAIL_CONTACT) as Contact?

            if (contact != null && contactForPhoneUpdate == null && contactForEmailUpdate == null) {
                contactViewModel!!.delete(contact!!)

            } else if (contact != null && contactForPhoneUpdate != null && contactForEmailUpdate == null) {
                contactForPhoneUpdate!!.id = contactId
                contactViewModel!!.update(contactForPhoneUpdate!!)

            } else if (contact != null && contactForPhoneUpdate == null && contactForEmailUpdate != null) {
                contactForEmailUpdate!!.id = contactId
                contactViewModel!!.update(contactForEmailUpdate!!)
            }
        }
    }

    companion object {
        const val CONTACT_ID = "CONTACT ID"
        const val ITEM = "ITEM"
        const val ADD_PHONE_CONTACT = 1
        const val ADD_EMAIL_CONTACT = 2
        const val EDIT_CONTACT = 3
    }
}
