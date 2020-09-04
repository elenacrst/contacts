package com.weightwatchers.ww_exercise_02

import com.weightwatchers.ww_exercise_02.model.Contact
import org.junit.Test

import org.junit.Assert.*

class ContactUnitTest {
    @Test
    fun phone_isValid() {
        assertTrue(Contact.isValidNumber("074-730-5900"))
    }

    @Test
    fun phone_isInvalid() {
        assertFalse(Contact.isValidNumber("074-730-59"))
    }
}