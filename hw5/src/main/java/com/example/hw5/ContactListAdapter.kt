package com.example.hw5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_contact_layout.view.*
import java.util.*


class ContactListAdapter : RecyclerView.Adapter<ContactListAdapter.ContactItemViewHolder>() {
    private var items: List<Contact> = ArrayList()
    private var mListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(contact: Contact)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact_layout, parent, false)
        return ContactItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactItemViewHolder, position: Int) {
        val currentContact: Contact = items[position]
        holder.itemIcon.setImageResource(currentContact.icon)
        holder.itemLine1.text = currentContact.line1
        holder.itemLine2.text = currentContact.line2
    }

    override fun getItemCount(): Int {
        return if (items != null) items.size else 0
    }

    fun setContacts(items: List<Contact>) {
        this.items = items 
        notifyDataSetChanged()
    }

    fun setMListener(mListener: OnItemClickListener?) {
        this.mListener = mListener
    }

    inner class ContactItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val itemIcon : ImageView = itemView.viewItemIcon
         val itemLine1 : TextView = itemView.viewItemLine1
         val itemLine2 : TextView = itemView.viewItemLine2

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (mListener != null && position != RecyclerView.NO_POSITION) {
                    mListener!!.onItemClick(items[position])
                }
            }
        }
    }
}
