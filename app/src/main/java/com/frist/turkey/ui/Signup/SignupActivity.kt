package com.frist.turkey.ui.Signup

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.text.isDigitsOnly
import com.frist.turkey.R


import com.frist.turkey.ui.OTP.OtpActivity
import com.frist.turkey.ui.home.HomeActivity
import com.frist.turkey.ui.login.LoginActivity
import com.frist.turkey.utils.AppConstant.EMAIL_PATTERN
import com.frist.turkey.utils.AppConstant.STRONG_PASSWORD
import com.frist.turkey.utils.showToast
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*

import kotlinx.android.synthetic.main.activity_signup.*
import java.util.concurrent.TimeUnit

class SignupActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mAuth: FirebaseAuth

    var type: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        initViews()
    }

    private fun initViews() {
        btnSignup.setOnClickListener(this)
        mAuth = FirebaseAuth.getInstance();


        etEmail_signup.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                //type 1 for number and type  2 for email
                if (etEmail_signup.text.toString().isDigitsOnly()) {

                    type = 1
                    countryCode_Signup.visibility = View.VISIBLE
                    // etPassword_Signup.visibility=View.INVISIBLE
                } else {
                    type = 2
                    //   etPassword_Signup.visibility=View.INVISIBLE
                    countryCode_Signup.visibility = View.GONE
                }

            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().isNullOrEmpty())
                    countryCode_Signup.visibility = View.GONE
            }

        })

    }

    fun isNumber(s: String): Boolean {
        return try {
            s.toInt()
            true
        } catch (ex: NumberFormatException) {
            false
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnSignup -> {
                when (type) {
                    1 -> {
                        showToast("Login with Number")
                          SignupUserWthNumber()

                    }
                    2 -> {
                        showToast("Login with Email")
                        SignupUserWthEmail()

                    }
                }

            }
        }
    }

    private fun SignupUserWthNumber() {


        var callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

                Log.d(TAG, "onVerificationCompleted:$credential")
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.w(TAG, "onVerificationFailed", e)

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }

                // Show a message and update the UI
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d(TAG, "onCodeSent:$verificationId")

                // Save verification ID and resending token so we can use them later

                val intent = Intent(this@SignupActivity, OtpActivity::class.java)
                intent.putExtra("OTP", verificationId)
                intent.putExtra("resendToken", token)
                intent.putExtra("phoneNumber", etEmail_signup.text.toString())
                startActivity(intent)

            }


        }


        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(countryCode_Signup.selectedCountryCodeWithPlus + etEmail_signup.text.toString())       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)


    }

    override fun onStart() {
        super.onStart()
        /*if (mAuth.currentUser!=null){
            startActivity(Intent(this,HomeActivity::class.java))
        }*/
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    showToast("signInWith_Number:success")
                    startActivity(Intent(this, HomeActivity::class.java))

                    val user = task.result?.user
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    showToast("signInWithCredential:failure ${task.exception}")
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }

    private fun SignupUserWthEmail() {
        var Email = etEmail_signup.text.toString()
        var Password = etPassword_Signup.text.toString()
        if (validateSignupUser(Email, Password)) {
            mAuth?.createUserWithEmailAndPassword(Email, Password)?.addOnCompleteListener {
                if (it.isSuccessful) {
                    startActivity(Intent(this, LoginActivity::class.java))
                    progress_signup.visibility = View.GONE
                    showToast("Signup Successful")
                } else {
                    showToast(it.result.toString())
                    showToast(it.exception.toString())
                }
            }


        }

    }

    fun validateSignupUser(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            showToast("Enter Email")
            return false
        } else if (!EMAIL_PATTERN.toRegex().matches(email)) {
            showToast("Enter Valid Email Address")
            return true
        } else if (password.isEmpty()) {
            showToast("Enter Password")
            return false
        } else if (!STRONG_PASSWORD.toRegex().matches(password)) {
            showToast("EnterValid  Password")
            return true
        }
        return true
    }
}