package com.frist.turkey.ui.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.frist.turkey.R
import com.frist.turkey.base.BaseActivity
import com.frist.turkey.ui.Signup.SignupActivity
import com.frist.turkey.ui.home.HomeActivity
import com.frist.turkey.utils.SharedPreferenceUtility
import com.frist.turkey.utils.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*


class LoginActivity : BaseActivity(), View.OnClickListener {

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initViews()
        initControl()
    }


   override fun initViews() {
        mAuth = FirebaseAuth.getInstance();
      // SharedPreferenceUtility.getInstance(this).isLoggedIn = true
        dont_have_account.setOnClickListener(this)



    }

    override fun initControl() {
        btnLogin.setOnClickListener {

            signin()

        }

    }

    private fun signin() {
        var email = etEmail.text.toString().trim()
        var password = etPassword.text.toString().trim()

        if(email.isNullOrEmpty()&&password.isNullOrEmpty()){
            showToast("Enter input detail")

        }
        else{
            pbLogin.visibility=View.VISIBLE
            mAuth?.signInWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        showToast("signInWithEmail:success")
                        startActivity(Intent(this, HomeActivity::class.java))
                        val currentUser = mAuth?.currentUser
                        //  updateUI(user)
                        pbLogin.visibility=View.GONE
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        pbLogin.visibility=View.GONE
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        //updateUI(null)
                    }
                }
        }


    }

    override fun onStart() {
        super.onStart()
        sharedPreferences.currentUser = mAuth?.currentUser.toString()
        //  updateUI(currentUser)

    }

    fun isNumber(str: String) = str.toDoubleOrNull()?.let { true } ?: false
    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.dont_have_account -> {
                startActivity(Intent(this, SignupActivity::class.java))

            }
        }
    }
}