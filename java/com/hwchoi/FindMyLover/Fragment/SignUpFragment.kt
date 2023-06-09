package com.hwchoi.FindMyLover.Fragment

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hwchoi.FindMyLover.Activity.LoginActivity
import com.hwchoi.FindMyLover.R
import com.hwchoi.FindMyLover.Utils.isEmailFormat
import com.hwchoi.FindMyLover.Utils.isPasswordFormat
import com.hwchoi.FindMyLover.Utils.isPhoneFormat
import com.hwchoi.FindMyLover.Utils.signUpFormatChecker

class SignUpFragment : Fragment() {

    private val Tag: String = this.javaClass.simpleName

    lateinit var auth: FirebaseAuth //파이어베이스 인증 객체

    lateinit var mInputId: String
    lateinit var mInputPassword: String
    lateinit var mInputConfirmPassword: String
    lateinit var mInputNickName: String
    lateinit var mInputName: String
    lateinit var mInputPhone: String

    lateinit var mDialog: AlertDialog.Builder

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
        val mNickNameEditText = view.findViewById<EditText>(R.id.sign_up_nick_name)
        val mPhoneEditText = view.findViewById<EditText>(R.id.sign_up_phone)

        val mSignUpBtn = view.findViewById<Button>(R.id.sign_up_btn)
        val mBackBtn = view.findViewById<Button>(R.id.sign_up_back_btn)

        mDialog = AlertDialog.Builder(activity)

        mBackBtn.setOnClickListener {
            mDialog
                .setMessage(activity.getString(R.string.sign_up_back_alert))
                .setPositiveButton(
                    activity.getString(R.string.button_ok)
                ) { _, _ ->
                    activity.showLoginFragment()
                }
                .setNegativeButton(
                    activity.getString(R.string.button_cancel)
                ) { _, _ ->
                }
            mDialog.create()
            mDialog.show()
        }

        mSignUpBtn.setOnClickListener {

            mInputId = mIdEditText.text.toString()
            mInputPassword = mPasswordEditText.text.toString()
            mInputConfirmPassword = mPasswordConfirmEditText.text.toString()
            mInputNickName = mNickNameEditText.text.toString()
            mInputName = mNameEditText.text.toString()
            mInputPhone = mPhoneEditText.text.toString()

            Log.i(
                Tag,
                "id ($mInputId), passwd ($mInputPassword), passwdCheck ($mInputConfirmPassword), nick ($mInputNickName), name ($mInputName), phone ($mInputPhone)"
            )

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
            } else if (signUpFormatChecker(mInputConfirmPassword) ||
                !mInputConfirmPassword.equals(mInputPassword)
            ) {
                mPasswordConfirmEditText.requestFocus()
                Toast.makeText(
                    activity,
                    getString(R.string.sign_up_pw_con_error),
                    Toast.LENGTH_SHORT
                ).show()
            } else if (signUpFormatChecker(mInputName)) {
                mNameEditText.requestFocus()
                Toast.makeText(activity, getString(R.string.sign_up_name_error), Toast.LENGTH_SHORT)
                    .show()
            } else if (signUpFormatChecker(mInputNickName)) {
                mNickNameEditText.requestFocus()
                Toast.makeText(activity, getString(R.string.sign_up_nick_error), Toast.LENGTH_SHORT)
                    .show()
            } else if (signUpFormatChecker(mInputPhone) || !isPhoneFormat(mInputPhone)) {
                mPhoneEditText.requestFocus()
                Toast.makeText(
                    activity,
                    getString(R.string.sign_up_phone_error),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                auth.createUserWithEmailAndPassword(mInputId, mInputPassword)
                    .addOnCompleteListener { task ->
                        run {
                            if (task.isSuccessful) {
                                auth.currentUser?.sendEmailVerification()
                                    ?.addOnCompleteListener { sendTask ->
                                        run {
                                            if (sendTask.isSuccessful) {
                                                mDialog
                                                    .setMessage(activity.getString(R.string.sign_up_done))
                                                    .setPositiveButton(activity.getString(R.string.button_ok)) { _, _ ->
                                                        activity.showLoginFragment()
                                                    }
                                                mDialog.create()
                                                mDialog.show()
                                            } else {
                                            }
                                        }
                                    }
                            } else {
                                mDialog
                                    .setMessage(activity.getString(R.string.sign_up_overlap_email))
                                    .setPositiveButton(activity.getString(R.string.button_ok)) { _, _ ->
                                    }
                                mDialog.create()
                                mDialog.show()
                                Log.e(Tag, "등록에 실패했습니다...", task.exception)
                            }
                        }
                    }
            }
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        auth = Firebase.auth
    }
    //TODO - 입력 데이터 검증, 아이디 중복 검사, 이메일 인증 대기 로직 만들기

}