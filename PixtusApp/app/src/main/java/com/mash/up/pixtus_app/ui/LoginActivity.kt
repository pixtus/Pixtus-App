package com.mash.up.pixtus_app.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.mash.up.pixtus_app.R
import com.mash.up.pixtus_app.base.BaseActivity
import com.mash.up.pixtus_app.ui.create.CreateStep1Activity
import kotlinx.android.synthetic.main.activity_login.*


class  LoginActivity : BaseActivity() {
    private var RC_SIGN_IN= 1111
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.mash.up.pixtus_app.R.layout.activity_login)
        auth = FirebaseAuth.getInstance()


        sign_in_button.setOnClickListener{
            signIn()
        }

        Glide.with(this).asGif().load(R.raw.pixtus_login).into(imageView)

    }

    override fun onStart() {
        super.onStart()
       /* val account = GoogleSignIn.getLastSignedInAccount(this)
        updateUI(account)*/
    }

    private fun signIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val googleApiClient = GoogleApiClient.Builder(this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()

        val intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
        startActivityForResult(intent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                handleSignInResult(task)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                e.printStackTrace()
                // ...
            }
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
            Log.d("LOG_TEST", account.toString())
            auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, StoryActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Snackbar.make(root_layout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
                        updateUI(null)
                    }

                    // ...
                }

            // updateUI(account)
        } catch (e: ApiException) {
            Log.d("LOG_TEST", e.toString())
            updateUI(null)
        }

    }

    fun updateUI(acct : GoogleSignInAccount?){

    }
}
