package com.mash.up.pixtus_app.ui

import android.os.Bundle
import com.mash.up.pixtus_app.R
import android.content.Intent
import android.os.Handler
import android.util.Log
import com.mash.up.pixtus_app.base.BaseActivity
import com.mash.up.pixtus_app.core.NetworkCore
import com.mash.up.pixtus_app.core.PixtusApi
import com.mash.up.pixtus_app.ui.create.CreateStep1Activity
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        NetworkCore.getNetworkCore<PixtusApi>()
            .getExcercises()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("list_data", it.toString())
            }, {
                it.printStackTrace()
            })

        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 1500)

    }
}
