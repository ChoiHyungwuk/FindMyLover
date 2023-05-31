package com.hwchoi.FindMyLover.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.hwchoi.FindMyLover.Fragment.LoginFragment
import com.hwchoi.FindMyLover.Fragment.SignUpFragment
import com.hwchoi.FindMyLover.R
import com.hwchoi.FindMyLover.Utils.mBackPressedDelay

var mEmailAdder: String? = null
var mPassword: String? = null
var mAutoLogin: Boolean = false

class LoginActivity : AppCompatActivity() {

    private var backpressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_view)
        showLoginFragment()
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() > backpressedTime + mBackPressedDelay) {
            backpressedTime = System.currentTimeMillis();
            Toast.makeText(this, getString(R.string.back_pressed_text), Toast.LENGTH_SHORT).show()
        } else if (System.currentTimeMillis() <= backpressedTime + mBackPressedDelay) {
            finishAffinity();
        }
    }

    fun showSignUpFragment(){
        val mFragmentManager: FragmentManager = supportFragmentManager
        val mTransaction: FragmentTransaction = mFragmentManager.beginTransaction()
        val fragment = SignUpFragment()
        mTransaction.replace(R.id.fragment_views,fragment)
        mTransaction.commit()
    }

    fun showLoginFragment(){
        val mFragmentManager: FragmentManager = supportFragmentManager
        val mTransaction: FragmentTransaction = mFragmentManager.beginTransaction()
        val fragment = LoginFragment()
        mTransaction.add(R.id.fragment_views,fragment)
        mTransaction.commit()
    }

    fun goToMain(){
        val loginIntent = Intent(this, MainActivity::class.java)
        startActivity(loginIntent)
    }

}