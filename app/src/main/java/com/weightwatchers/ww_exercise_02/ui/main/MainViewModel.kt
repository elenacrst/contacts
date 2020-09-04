package com.weightwatchers.ww_exercise_02.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weightwatchers.ww_exercise_02.model.Contact

class MainViewModel : ViewModel() {
    private var _contacts: MutableLiveData<List<Contact>> = MutableLiveData()
    val contacts: LiveData<List<Contact>>
        get() = _contacts

    init {
        _contacts.value = listOf()
    }

    fun addContact(contact: Contact) {
        _contacts.value = _contacts.value!!.plus(contact)
    }

    fun removeContact(contact: Contact) {
        _contacts.value = _contacts.value!!.minus(contact)
    }
}