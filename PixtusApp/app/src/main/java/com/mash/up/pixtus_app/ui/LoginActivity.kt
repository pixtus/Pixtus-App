package com.mash.up.pixtus_app.ui

import android.content.Intent
import android.net.Network
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.mash.up.pixtus_app.R
import com.mash.up.pixtus_app.base.BaseActivity
import com.mash.up.pixtus_app.core.NetworkCore
import com.mash.up.pixtus_app.core.PixtusApi
import com.mash.up.pixtus_app.ui.create.CreateStep1Activity
import com.mash.up.pixtus_app.utils.showToastMessageString
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.HttpException


class   LoginActivity : BaseActivity() {
    private var RC_SIGN_IN= 1111
    lateinit var gso : GoogleSignInOptions
    val mGoogleSignInClient by lazy {
        GoogleSignIn.getClient(this, gso)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.mash.up.pixtus_app.R.layout.activity_login)
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        sign_in_button.setOnClickListener{
            signIn()
        }

        Glide.with(this).asGif().load(R.raw.pixtus_login).into(imageView)

    }

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        updateUI(account)
    }

    private fun signIn(){
        val signIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {

            NetworkCore.getNetworkCore<PixtusApi>().loginUser(hashMapOf("login" to "yjh54240@naver.com"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    showCode(it.response()?.code())
                }, {
                    if(it is HttpException)
                        showCode(it.code())

                })
        }
    }

    private fun showCode(code : Int?){
        when(code){
            200 -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            204 ->{
                val intent = Intent(this, CreateStep1Activity::class.java)
                startActivity(intent)
                finish()
            }
            else -> fail()
        }
    }


    private fun fail(){
        showToastMessageString("오류가 발생 했습니다.")
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            updateUI(account)
        } catch (e: ApiException) {
            updateUI(null)
        }

    }

    fun updateUI(acct : GoogleSignInAccount?){

    }
}
