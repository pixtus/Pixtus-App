package com.mash.up.pixtus_app.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.mash.up.pixtus_app.ui.login.PostLoginRequest
import com.mash.up.pixtus_app.ui.login.data.PostLoginResponse
import com.mash.up.pixtus_app.utils.SharedPreferenceController
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.json.JSONObject


class LoginActivity : BaseActivity() {
    private var RC_SIGN_IN = 1111
    lateinit var gso: GoogleSignInOptions
    val mGoogleSignInClient by lazy {
        GoogleSignIn.getClient(this, gso)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.mash.up.pixtus_app.R.layout.activity_login)
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        sign_in_button.setOnClickListener {
            signIn()
        }

        Glide.with(this).asGif().load(R.raw.pixtus_login).into(imageView)

    }

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        updateUI(account)
    }

    private fun signIn() {
        val signIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            //val intent = Intent(this, CreateStep1Activity::class.java)
//            val intent = Intent(this, StoryActivity::class.java)
//            startActivity(intent)
//            finish()
            var task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            Log.e("이것이여~", account!!.account.toString() + account.displayName + account.email + account.id + " ")
            updateUI(account)
        } catch (e: ApiException) {
            updateUI(null)
            Log.e("이것이여~", Log.getStackTraceString(e))
        }

    }

    fun updateUI(acct: GoogleSignInAccount?) {
        if (acct != null) {
            postLogin(acct.id, acct.email, acct.displayName.toString())
            // TODO: 이름 확인
        }
    }

    private fun postLogin(uid: String?, email: String?, name: String) {

//        val paramObject = JSONObject()
//        println("uid : " + uid.toString())
//        println("email : " + email.toString())
//        paramObject.put("uid", uid)
//        paramObject.put("email", email)

        NetworkCore.getNetworkCore<PixtusApi>()
            .postLogin(
                "application/json",
                PostLoginRequest(email, uid)
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                // 등록된 ID 존재 X, 회원가입
                if (it.code() == 204) {
                    startActivity<CreateStep1Activity>("uid" to uid, "email" to email, "name" to name)
                    finish()
                }// 등록된 ID 존재
                else if (it.code() == 200) {
                    // 헤더 받아서 SharedPreference에 저장
                    Log.e("여재가", it.headers().get("Authorization"))
                    SharedPreferenceController.setAuthorization(this@LoginActivity, it.headers().get("Authorization"))
                    SharedPreferenceController.setUid(this@LoginActivity, uid)
                    startActivity<ViewActivity>()
                    finish()
                } else
                    Log.e("Login Act err", "error code : " + it.code().toString())

                // saveUserInfoToDevice(it.body()!!)
            }, {
                Log.e("LoginAct Error", Log.getStackTraceString(it))
            })
    }
}
