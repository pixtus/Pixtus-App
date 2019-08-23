package com.mash.up.pixtus_app.ui.history.fragment


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.mash.up.pixtus_app.R
import com.mash.up.pixtus_app.ui.history.adapter.HistoryPageTabAdapter
import kotlinx.android.synthetic.main.fragment_history.*

/**
 * A simple [Fragment] subclass.
 * Use the [HistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class HistoryFragment : Fragment() {

    private val historyPageTabAdapter by lazy {
        HistoryPageTabAdapter(2, childFragmentManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment HistoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            HistoryFragment()
    }
}
