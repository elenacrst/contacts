package com.weightwatchers.ww_exercise_02.adapter

import androidx.recyclerview.widget.RecyclerView
import com.weightwatchers.ww_exercise_02.databinding.LayoutContactBinding
import com.weightwatchers.ww_exercise_02.model.Contact

/**
 * A view holder that represent the contact. 3 fields are:
 * - contactNumberView = the contact's phoneNumber in the list
 * - contactNameView = the contact's name
 * - phoneNumberView = the contact's phone phoneNumber
 */
class ContactViewHolder(private val binding: LayoutContactBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(contact: Contact, index: Int, openDialer: (position: Int) -> Unit, longClickListener: (position: Int) -> Unit) {
        binding.contact = contact
        binding.index = index + 1
        binding.baseContainer.setOnClickListener {
            openDialer(adapterPosition)
        }
        binding.baseContainer.setOnLongClickListener {
            longClickListener(adapterPosition)
            true
        }
    }


}