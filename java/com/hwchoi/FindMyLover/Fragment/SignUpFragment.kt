package com.hwchoi.FindMyLover.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hwchoi.FindMyLover.Activity.LoginActivity
import com.hwchoi.FindMyLover.R

class SignUpFragment : Fragment() {

    lateinit var auth: FirebaseAuth //파이어베이스 인증 객체
    lateinit var mInputEmail: String
    lateinit var mInputPassword: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.sign_up_layout, container, false)
        val activity = activity as LoginActivity

        val mEmailEditText = view.findViewById<EditText>(R.id.sign_up_id)
        val mPasswordEditText = view.findViewById<EditText>(R.id.sign_up_password)

        val mSignUpBtn = view.findViewById<Button>(R.id.sign_up_btn)
        val mBackBtn = view.findViewById<Button>(R.id.sign_up_back_btn)

        mBackBtn.setOnClickListener {
            activity.showSignUpFragment()
        }

        mSignUpBtn.setOnClickListener {
            mInputEmail = mEmailEditText.text.toString()
            mInputPassword = mPasswordEditText.text.toString()

            println("아이디 ($mInputEmail) 패스워드 ($mInputPassword)")

            auth.createUserWithEmailAndPassword(mInputEmail, mInputPassword)
                .addOnCompleteListener { task ->
                    run {
                        if (task.isSuccessful) {
                            auth.currentUser?.sendEmailVerification()
                                ?.addOnCompleteListener { sendTask ->
                                    run {
                                        if (sendTask.isSuccessful) {

                                        } else {

                                        }
                                    }
                                }
                        } else {
                            Log.e("SignUpError", "등록에 실패했습니다.", task.exception)
                        }
                    }
                }

//            activity.showSignUpFragment()
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        auth = Firebase.auth
    }

}