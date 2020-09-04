package com.weightwatchers.ww_exercise_02.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.regex.Matcher
import java.util.regex.Pattern

@Parcelize
data class Contact(
        val name: String,
        val phoneNumber: String
) : Parcelable {

    companion object {
        fun isValidNumber(number: String): Boolean {//e.g. 111-111-1111
            val p: Pattern = Pattern.compile("\\d{3}-?\\d{3}-?\\d{4}")
            val m: Matcher = p.matcher(number)
            return m.matches()
        }
    }

}