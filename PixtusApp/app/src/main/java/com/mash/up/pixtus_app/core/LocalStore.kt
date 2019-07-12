package com.mash.up.pixtus_app.core

import android.content.Context
import android.content.Context.MODE_PRIVATE
import java.math.MathContext


class LocalStore(val context: Context){

    var pref = context.getSharedPreferences("PREF_STORE", MODE_PRIVATE)


    fun setUid(uid : String){
        var editor = pref.edit()
        editor.putString("uid", uid)
        editor.commit()
    }

    fun getUid() = pref.getString("uid","")
}