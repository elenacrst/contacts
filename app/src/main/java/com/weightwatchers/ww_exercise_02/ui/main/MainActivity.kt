package com.weightwatchers.ww_exercise_02.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.weightwatchers.ww_exercise_02.R
import com.weightwatchers.ww_exercise_02.adapter.ContactAdapter
import com.weightwatchers.ww_exercise_02.model.Contact
import com.weightwatchers.ww_exercise_02.ui.ContactActivity


class MainActivity : AppCompatActivity() {
    private var contactAdapter: ContactAdapter? = ContactAdapter {
        val openContactIntent = Intent(this, ContactActivity::class.java)
        openContactIntent.putExtra(ContactActivity.EXTRA_CONTACT, it)
        startActivityForResult(openContactIntent, RC_EDIT)
    }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        setupViews()
        viewModel.contacts.observe(this, Observer {
            contactAdapter?.setData(it)
        })
    }

    private fun setupViews() {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val recyclerView = findViewById<View>(R.id.recycler_view) as RecyclerView
        recyclerView.adapter = contactAdapter

        val addContactButton = findViewById<View>(R.id.add_contact) as FloatingActionButton
        addContactButton.setOnClickListener {
            val openContactIntent = Intent(this, ContactActivity::class.java)
            startActivityForResult(openContactIntent, RC_ADD)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (resultCode == RESULT_OK) {
            if (requestCode == RC_ADD) {
                val contact = intent!!.getParcelableExtra<Contact>(ContactActivity.EXTRA_CONTACT)
                viewModel.addContact(contact!!)
            } else if (requestCode == RC_EDIT) {
                val oldContact = intent!!.getParcelableExtra<Contact>(ContactActivity.EXTRA_CONTACT_OLD)!!
                val newContact = intent.getParcelableExtra<Contact>(ContactActivity.EXTRA_CONTACT_NEW)!!
                viewModel.removeContact(oldContact)
                viewModel.addContact(newContact)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main_activity, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mode) {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            finish()
            startActivity(Intent(this@MainActivity, this@MainActivity.javaClass))
            return true


        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val RC_ADD = 102
        const val RC_EDIT = 103
    }
}