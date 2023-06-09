package com.hwchoi.FindMyLover.Utils

import android.content.Context
import android.util.Patterns
import android.widget.Toast


var mContext: Context? = null

val mBackPressedDelay: Int = 2000
val mIntroDelay: Int = 3000

fun SetContext(context: Context) {
    mContext = context
}

val phonePattern = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$".toRegex()
val passwordPattern =
    "^(?=.*[a-zA-Z0-9])(?=.*[a-zA-Z!@#\$%^&*])(?=.*[0-9!@#\$%^&*]).{6,15}\$".toRegex()

fun isPhoneFormat(pwd: String): Boolean {
    return pwd.matches(phonePattern)
}

fun isPasswordFormat(pwd: String): Boolean {
    return pwd.matches(passwordPattern)
}

fun isEmailFormat(email: String) : Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun signUpFormatChecker(text: String): Boolean {
    if (text.isEmpty()) {
        return true
    }
    return false
}

class Utils {

}