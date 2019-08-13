package com.mash.up.pixtus_app.ui.history.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.mash.up.pixtus_app.R
import com.mash.up.pixtus_app.core.HistoryApi
import com.mash.up.pixtus_app.core.NetworkCore
import com.mash.up.pixtus_app.core.PixtusApi
import com.mash.up.pixtus_app.ui.history.adapter.HistoryPageTabAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_history.*


/**
 * Created by TakHyeongMin on 2019-07-25.
 */

class HistoryFragment : Fragment() {

    private val historyPageTabAdapter by lazy {
        HistoryPageTabAdapter(2, childFragmentManager)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_history, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setTabLayout()
    }

    private fun setTabLayout() {
        view_pager.adapter = historyPageTabAdapter
        tablayout.setupWithViewPager(view_pager)

        val headerView: View = (activity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            .inflate(R.layout.tab_layout_fragment_history, null, false)
        val useKcal = headerView.findViewById(R.id.btn_use_kcal) as RelativeLayout
        val getKcal = headerView.findViewById(R.id.btn_get_kcal) as RelativeLayout

        tablayout.getTabAt(0)!!.customView = useKcal
        tablayout.getTabAt(1)!!.customView = getKcal
    }
}