package com.hwchoi.FindMyLover.Activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.hwchoi.FindMyLover.R
import com.hwchoi.FindMyLover.Utils.mIntroDelay

class InitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.intro_layout)

        //TODO -> 자동 로그인 값 확인
        // 확인 되면 메인 안되면 로그인 화면
    }

    override fun onResume() {
        super.onResume()

        Handler().postDelayed({
            GoToLogin()
        }, mIntroDelay.toLong())
    }

    private fun GoToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun GoToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onBackPressed() {
    }

}