package com.hwchoi.FindMyLover.Activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hwchoi.FindMyLover.R
import com.hwchoi.FindMyLover.Utils.mIntroDelay

class InitActivity : AppCompatActivity() {

    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted.
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted.
            } else -> {
            // No location access granted.
        }
        }
    }

    lateinit var auth: FirebaseAuth //파이어베이스 인증 객체

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.intro_layout)

        //TODO -> 자동 로그인 값 확인
        // 확인 되면 메인 안되면 로그인 화면
    }

    override fun onResume() {
        super.onResume()
        auth = Firebase.auth

        Handler().postDelayed({
            autoLogin()
        }, mIntroDelay.toLong())


//        when {
//            ContextCompat.checkSelfPermission(
//                CONTEXT,
//                Manifest.permission.
//            ) == PackageManager.PERMISSION_GRANTED -> {
//                // You can use the API that requires the permission.
//            }
//            shouldShowRequestPermissionRationale(...) -> {
//            // In an educational UI, explain to the user why your app requires this
//            // permission for a specific feature to behave as expected, and what
//            // features are disabled if it's declined. In this UI, include a
//            // "cancel" or "no thanks" button that lets the user continue
//            // using your app without granting the permission.
//            showInContextUI(...)
//        }
//            else -> {
//                // You can directly ask for the permission.
//                // The registered ActivityResultCallback gets the result of this request.
//                requestPermissionLauncher.launch(
//                    Manifest.permission.REQUESTED_PERMISSION)
//            }
//        }



// ...

// Before you perform the actual permission request, check whether your app
// already has the permissions, and whether your app needs to show a permission
// rationale dialog. For more details, see Request permissions.
        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))

    }

    private fun GoToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun GoToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Continue the action or workflow in your
                // app.
            } else {
                // Explain to the user that the feature is unavailable because the
                // feature requires a permission that the user has denied. At the
                // same time, respect the user's decision. Don't link to system
                // settings in an effort to convince the user to change their
                // decision.
            }
        }

    override fun onBackPressed() {
    }

    private fun autoLogin() {
        val sharedPref = getSharedPreferences(
            getString(R.string.auto_login_key), Context.MODE_PRIVATE
        )

        if (sharedPref.getBoolean("autoCheck", false)) {
            auth.signInWithEmailAndPassword(
                sharedPref.getString("id", null).toString(),
                sharedPref.getString("passwd", null).toString()
            )
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        GoToMain()
                    } else {
                        GoToLogin()
                    }
                }
        } else {
            GoToLogin()
        }
    }

}