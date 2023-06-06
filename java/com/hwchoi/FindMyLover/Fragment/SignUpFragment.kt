package com.hwchoi.FindMyLover.Fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hwchoi.FindMyLover.Activity.LoginActivity
import com.hwchoi.FindMyLover.R

class SignUpFragment : Fragment() {

    lateinit var auth: FirebaseAuth //파이어베이스 인증 객체
    lateinit var mInputId: String
    lateinit var mInputPassword: String

    lateinit var mInputEmail: String
    lateinit var mInputConfirmPassword: String
    lateinit var mInputNickName: String
    lateinit var mInputName: String
    lateinit var mInputPhone: String

    lateinit var mPhoneDropbox: Spinner
    lateinit var mEmailDropbox: Spinner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.sign_up_layout, container, false)
        val activity = activity as LoginActivity

        val mIdEditText = view.findViewById<EditText>(R.id.sign_up_id)
        val mPasswordEditText = view.findViewById<EditText>(R.id.sign_up_password)
        val mPasswordConfirmEditText = view.findViewById<EditText>(R.id.sign_up_password_check)
        val mNameEditText = view.findViewById<EditText>(R.id.sign_up_name)
        val mNickNameEditText = view.findViewById<EditText>(R.id.sign_up_name)
        val mEmailEditText = view.findViewById<EditText>(R.id.sign_up_email)
        val mPhoneEditText = view.findViewById<EditText>(R.id.sign_up_phone)

        val mSignUpBtn = view.findViewById<Button>(R.id.sign_up_btn)
        val mBackBtn = view.findViewById<Button>(R.id.sign_up_back_btn)

        mPhoneDropbox = view.findViewById<Spinner>(R.id.phone_dropbox)
        mEmailDropbox = view.findViewById<Spinner>(R.id.email_dropbox)

        setupDropBox(activity)

        mBackBtn.setOnClickListener {
            activity.showLoginFragment()
        }

        mSignUpBtn.setOnClickListener {
            mInputId = mIdEditText.text.toString()
            mInputPassword = mPasswordEditText.text.toString()

            auth.createUserWithEmailAndPassword(mInputId, mInputPassword)
                .addOnCompleteListener { task ->
                    run {
                        if (task.isSuccessful) {
                            auth.currentUser?.sendEmailVerification()
                                ?.addOnCompleteListener { sendTask ->
                                    run {
                                        if (sendTask.isSuccessful) {

                                        } else {
                                            Log.e("SignUpError", "등록에 실패했습니다.", task.exception)
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

    fun setupDropBox (context: Context) {
        ArrayAdapter.createFromResource(
            context,
            R.array.phone,
            R.layout.spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(R.layout.spinner_item)
            // Apply the adapter to the spinner
            mPhoneDropbox.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            context,
            R.array.email,
            R.layout.spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(R.layout.spinner_item)
            // Apply the adapter to the spinner
            mEmailDropbox.adapter = adapter
        }
    }

    //TODO - 입력 데이터 검증, 아이디 중복 검사, 이메일 인증 대기 로직 만들기

}