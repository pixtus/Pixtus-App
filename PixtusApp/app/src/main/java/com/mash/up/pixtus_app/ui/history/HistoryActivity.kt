package com.mash.up.pixtus_app.ui.history

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.mash.up.pixtus_app.R
import com.mash.up.pixtus_app.core.NetworkCore
import com.mash.up.pixtus_app.core.PixtusApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

//        NetworkCore.getNetworkCore<PixtusApi>()
//            .getExcercises()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                Log.d("list_data", it.toString())
//            }, {
//                it.printStackTrace()
//            })
    }
}
