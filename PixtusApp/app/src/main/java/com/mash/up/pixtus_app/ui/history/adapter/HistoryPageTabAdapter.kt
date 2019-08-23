package com.mash.up.pixtus_app.ui.history.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.mash.up.pixtus_app.ui.history.fragment.HistoryGetTabFragment
import com.mash.up.pixtus_app.ui.history.fragment.HistoryUseTabFragment


/**
 * Created by TakHyeongMin on 2019-07-25.
 */
class HistoryPageTabAdapter(val tabCount: Int, val fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    val useKcalTab : Fragment = HistoryUseTabFragment.newInstance()
    val getKcalTab : Fragment = HistoryGetTabFragment.newInstance()

    override fun getItem(p0: Int): Fragment? {
        return when(p0){
            0 -> useKcalTab
            1 -> getKcalTab
            else -> null
        }
    }

    override fun getCount(): Int = tabCount
}