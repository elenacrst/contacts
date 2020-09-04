package com.weightwatchers.ww_exercise_02.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar
import com.weightwatchers.ww_exercise_02.R
import com.weightwatchers.ww_exercise_02.model.Contact
import com.weightwatchers.ww_exercise_02.util.hideKeyboard

class ContactActivity : AppCompatActivity() {
    private var rootView: CoordinatorLayout? = null
    private var nameEditText: EditText? = null
    private var numberEditText: EditText? = null

    private var oldContact: Contact? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_contact)
        setupViews()
    }

    private fun setupViews() {
        rootView = findViewById<View>(R.id.coordinator_layout) as CoordinatorLayout

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        nameEditText = findViewById<View>(R.id.name_edit_text) as EditText
        numberEditText = findViewById<View>(R.id.number_edit_text) as EditText

        val saveButton = findViewById<View>(R.id.save_button) as Button
        saveButton.setOnClickListener { saveContact() }

        if (intent.hasExtra(EXTRA_CONTACT)) {
            oldContact = intent.getParcelableExtra(EXTRA_CONTACT)!!
            nameEditText!!.text.append(oldContact!!.name)
            numberEditText!!.text.append(oldContact!!.phoneNumber)
        }

        numberEditText!!.addTextChangedListener(createFormatTextWatcher())
        numberEditText!!.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                    actionId == EditorInfo.IME_ACTION_NEXT) {
                hideKeyboard()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun createFormatTextWatcher(): TextWatcher? {
        return object : TextWatcher {
            private var isRightAfterFormat = false

            override fun afterTextChanged(p0: Editable?) {
                p0?.toString()?.let {
                    if (!isRightAfterFormat) {
                        isRightAfterFormat = true
                        if (it.length <= 6) {
                            val number = it.replaceFirst("(\\d{3})-*(\\d+)".toRegex(), "$1-$2")
                            numberEditText!!.text.clear()
                            numberEditText!!.append(number)
                        } else if (it.length <= 9) {

                            val number = it.replaceFirst("(\\d{3})-(\\d{3})-*(\\d+)".toRegex(), "$1-$2-$3")
                            numberEditText!!.text.clear()
                            numberEditText!!.append(number)

                        }
                        isRightAfterFormat = false

                    }
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        }
    }

    private fun saveContact() {
        var contactName = ""
        var contactNumber = ""

        if (nameEditText!!.text != null && numberEditText!!.text != null) {
            contactName = nameEditText!!.text.toString()
            contactNumber = numberEditText!!.text.toString()
        }

        if (contactName.isEmpty() || contactNumber.isEmpty()) {
            hideKeyboard()
            Snackbar.make(rootView!!, "Unable to save contact. Please check all fields", Snackbar.LENGTH_LONG)
                    .show()
        } else if (!Contact.isValidNumber(contactNumber)) {
            hideKeyboard()
            Snackbar.make(rootView!!, "Unable to save contact. Please use a valid US phone number format: XXX-XXX-XXXX", Snackbar.LENGTH_LONG)
                    .show()
        } else {
            val contact = Contact(contactName, contactNumber)
            val data = Intent()
            oldContact?.let {
                data.putExtra(EXTRA_CONTACT_NEW, contact)
                data.putExtra(EXTRA_CONTACT_OLD, oldContact)
            } ?: kotlin.run {
                data.putExtra(EXTRA_CONTACT, contact)
            }

            setResult(RESULT_OK, data)
            finish()
        }

    }

    companion object {
        const val EXTRA_CONTACT = "Contact"
        const val EXTRA_CONTACT_NEW = "New Contact"
        const val EXTRA_CONTACT_OLD = "Old Contact"
    }
}