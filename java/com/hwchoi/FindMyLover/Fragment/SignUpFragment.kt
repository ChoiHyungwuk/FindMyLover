package com.hwchoi.FindMyLover.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.hwchoi.FindMyLover.Activity.LoginActivity
import com.hwchoi.FindMyLover.R

class SignUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.sign_up_layout, container, false)
        val activity = activity as LoginActivity

        val mSignUpBtn = view.findViewById<Button>(R.id.sign_up_btn)
        val mBackBtn = view.findViewById<Button>(R.id.sign_up_back_btn)

        mBackBtn.setOnClickListener {
            activity.showSignUpFragment()
        }

        mSignUpBtn.setOnClickListener {
            activity.showSignUpFragment()
        }

        return view
    }

}