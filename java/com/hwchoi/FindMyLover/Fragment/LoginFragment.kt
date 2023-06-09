package com.hwchoi.FindMyLover.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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

    lateinit var auth: FirebaseAuth //파이어베이스 인증 객체

    lateinit var mInputId: String
    lateinit var mInputPassword: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.login_layout, container, false)

        val mLoginBtn: Button = view.findViewById(R.id.go_main_btn)
        val mSignUpBtn: Button = view.findViewById(R.id.sign_up_btn)

        val mIdEditText = view.findViewById<EditText>(R.id.email_edit_text)
        val mPasswordEditText = view.findViewById<EditText>(R.id.passwd_edit_text)

        val activity = activity as LoginActivity

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
                        if (task.isSuccessful) {
                            activity.goToMain()
                        } else {

                        }
                    }
            }
        }
        mSignUpBtn.setOnClickListener {
            activity.showSignUpFragment()
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        auth = Firebase.auth
    }

}