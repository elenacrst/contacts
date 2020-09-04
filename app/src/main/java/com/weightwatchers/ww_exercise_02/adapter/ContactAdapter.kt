package com.weightwatchers.ww_exercise_02.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.weightwatchers.ww_exercise_02.ContactsApplication
import com.weightwatchers.ww_exercise_02.databinding.LayoutContactBinding
import com.weightwatchers.ww_exercise_02.model.Contact

class ContactAdapter(private val longClickListener: (contact: Contact) -> Unit) : RecyclerView.Adapter<ContactViewHolder>() {
    private var contactList = arrayListOf<Contact>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutContactBinding.inflate(layoutInflater, parent, false)


        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contactList[position]
        holder.bind(contact, position, openDialer = {
            openDialer(contactList[it])
        }, longClickListener = {
            longClickListener(contactList[it])
        })
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    fun setData(list: List<Contact>) {
        contactList.clear()
        contactList.addAll(list)
        notifyDataSetChanged()
    }

    private fun openDialer(contact: Contact) {
        val openDialerIntent = Intent(Intent.ACTION_DIAL)
        openDialerIntent.data = Uri.parse("tel:${contact.phoneNumber}")
        openDialerIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        ContactsApplication.contactsApplicationContext.startActivity(openDialerIntent)
    }
}