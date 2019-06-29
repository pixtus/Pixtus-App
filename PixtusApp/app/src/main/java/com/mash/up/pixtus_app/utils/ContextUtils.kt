package com.mash.up.pixtus_app.utils

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import android.widget.Toast


fun Context?.showToastMessage(id: Int) {
    if (this == null) return
    Toast.makeText(this.applicationContext, this.resources.getString(id), Toast.LENGTH_SHORT).show()
}

fun Fragment?.showToastMessage(id: Int) {
    if (this == null) return
    Toast.makeText(this.context?.applicationContext, this.resources.getString(id), Toast.LENGTH_SHORT).show()
}

fun Activity?.showToastMessage(id: Int) {
    if (this == null) return
    Toast.makeText(this.applicationContext, this.resources.getString(id), Toast.LENGTH_SHORT).show()
}

fun Activity?.showLongToastMessage(id : Int){
    if (this == null) return
    Toast.makeText(this.applicationContext, this.resources.getString(id), Toast.LENGTH_LONG).show()
}

fun Activity?.showLongToastMessageString(str: String){
    if (this == null) return
    Toast.makeText(this.applicationContext, str, Toast.LENGTH_LONG).show()
}


fun Context?.showToastMessageString(resId: Int) {
    if (this == null) return
    Toast.makeText(this.applicationContext, this.resources.getString(resId), Toast.LENGTH_SHORT).show()
}

fun Fragment?.showToastMessageString(resId: Int) {
    if (this == null) return
    Toast.makeText(this.context?.applicationContext, this.resources.getString(resId), Toast.LENGTH_SHORT).show()
}


fun Context?.showToastMessageString(msg: String?) {
    if (this == null) return
    Toast.makeText(this.applicationContext, msg, Toast.LENGTH_SHORT).show()
}


fun Fragment?.showToastMessageString(msg: String?) {
    if (this == null) return
    Toast.makeText(this.context?.applicationContext, msg, Toast.LENGTH_SHORT).show()
}
