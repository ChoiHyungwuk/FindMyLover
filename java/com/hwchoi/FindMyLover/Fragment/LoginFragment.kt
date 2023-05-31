package com.hwchoi.FindMyLover.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.hwchoi.FindMyLover.Activity.LoginActivity
import com.hwchoi.FindMyLover.R

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.login_layout, container, false)

        val mLoginBtn: Button = view.findViewById(R.id.go_main_btn)
        val mSignUpBtn: Button = view.findViewById(R.id.sign_up_btn)

        val activity = activity as LoginActivity

        mLoginBtn.setOnClickListener {
            activity.goToMain()
        }
        mSignUpBtn.setOnClickListener {

            activity.showSignUpFragment()
        }

        return view
    }

}