package com.mash.up.pixtus_app.utils

import android.content.Context
import android.util.Log
import org.json.JSONArray
import org.json.JSONException


/**
 * Created by TakHyeongMin on 2019-08-23.
 */
object SharedPreferenceController {

    // 변수부
    private val USER_NAME = "MYKEY"
    private val GOOGLE_KEY= "GOOGLE_KEY"
    private val CHARACTER_LEVEL= "CHARACTER_LEVEL"
    private val UID= "UID"
    private val CHARACTER_NAME= "CHARACTER_NAME"
    private val NAME = "NAME"

    fun setAuthorization(context: Context, authorization : String?){
        val pref = context.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE) //현재 내 기기에서만 볼수 있는 데이터
        val editor = pref.edit()
        editor.putString(GOOGLE_KEY, authorization)
        editor.commit()
    }

    fun getAuthorization(context: Context) : String {
        val pref = context.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE) //현재 내 기기에서만 볼수 있는 데이터
        return pref.getString(GOOGLE_KEY, "")
    }

    fun setUid(context: Context, uid : String?){
        val pref = context.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE) //현재 내 기기에서만 볼수 있는 데이터
        val editor = pref.edit()
        editor.putString(UID, uid)
        editor.commit()
    }

    fun getUid(context: Context) : String {
        val pref = context.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE) //현재 내 기기에서만 볼수 있는 데이터
        return pref.getString(UID, "")
    }

    fun setCharacterName(context: Context, nickName : String){
        val pref = context.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE) //현재 내 기기에서만 볼수 있는 데이터
        val editor = pref.edit()
        editor.putString(CHARACTER_LEVEL, nickName)
        editor.commit()
    }

    fun getCharacterName(context: Context) : String {
        val pref = context.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE) //현재 내 기기에서만 볼수 있는 데이터
        return pref.getString(CHARACTER_LEVEL, "")
    }

    fun clearSPC(context: Context){
        val pref = context.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE) //현재 내 기기에서만 볼수 있는 데이터
        val editor = pref.edit()
        editor.clear()
        editor.commit()
    }


}