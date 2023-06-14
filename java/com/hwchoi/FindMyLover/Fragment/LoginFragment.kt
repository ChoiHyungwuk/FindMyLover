package com.hwchoi.FindMyLover.Fragment

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hwchoi.FindMyLover.Activity.LoginActivity
import com.hwchoi.FindMyLover.R
import com.hwchoi.FindMyLover.Utils.isEmailFormat
import com.hwchoi.FindMyLover.Utils.isPasswordFormat
import com.hwchoi.FindMyLover.Utils.signUpFormatChecker

class LoginFragment : Fragment() {

    lateinit var activity: Activity
    lateinit var auth: FirebaseAuth //파이어베이스 인증 객체

    lateinit var mInputId: String
    lateinit var mInputPassword: String

    lateinit var mLoginBtn: Button
    lateinit var mSignUpBtn: Button

    lateinit var mIdEditText: EditText
    lateinit var mPasswordEditText: EditText

    lateinit var mAutoLoginCheckBox: CheckBox

    lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.login_layout, container, false)

        mLoginBtn = view.findViewById<Button>(R.id.go_main_btn)
        mSignUpBtn = view.findViewById<Button>(R.id.sign_up_btn)

        mIdEditText = view.findViewById<EditText>(R.id.email_edit_text)
        mPasswordEditText = view.findViewById<EditText>(R.id.passwd_edit_text)

        mAutoLoginCheckBox = view.findViewById<CheckBox>(R.id.auto_login)

        sharedPref = activity.getSharedPreferences(
            getString(R.string.auto_login_key), Context.MODE_PRIVATE
        )

        mLoginBtn.setOnClickListener {
            mInputId = mIdEditText.text.toString()
            mInputPassword = mPasswordEditText.text.toString()

            if (signUpFormatChecker(mInputId) || !isEmailFormat(mInputId)) {
                mIdEditText.requestFocus()
                Toast.makeText(activity, getString(R.string.sign_up_id_error2), Toast.LENGTH_SHORT)
                    .show()
            } else if (signUpFormatChecker(mInputPassword) || !isPasswordFormat(mInputPassword)) {
                mPasswordEditText.requestFocus()
                Toast.makeText(
                    activity,
                    getString(R.string.sign_up_pw_error),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                auth.signInWithEmailAndPassword(mInputId, mInputPassword)
                    .addOnCompleteListener(activity) { task ->
                        mPasswordEditText.text.clear()
                        if (task.isSuccessful) {
                            if (mAutoLoginCheckBox.isChecked) {
                                with(sharedPref.edit()) {
                                    putBoolean("autoCheck", true)
                                    putString("id", mInputId)
                                    putString("passwd", mInputPassword)
                                    apply()
                                }
                            }
                            (activity as LoginActivity).goToMain()
                        } else {

                        }
                    }
            }
        }
        mSignUpBtn.setOnClickListener {
            (activity as LoginActivity).showSignUpFragment()
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as LoginActivity
    }

    override fun onResume() {
        super.onResume()
        auth = Firebase.auth

    }

}