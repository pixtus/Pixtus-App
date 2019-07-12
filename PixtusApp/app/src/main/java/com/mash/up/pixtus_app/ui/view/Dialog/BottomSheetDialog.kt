package com.mash.up.pixtus_app.ui.view.Dialog

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.bottom_sheet_dialog.*
import android.widget.TextView
import android.support.constraint.ConstraintLayout
import com.mash.up.pixtus_app.ui.view.WorkoutListActivity
import kotlinx.android.synthetic.main.bottom_sheet_dialog.bottonSheet_history
import kotlinx.android.synthetic.main.bottom_sheet_dialog.bottonSheet_workout
import kotlinx.android.synthetic.main.bottom_sheet_dialog.view.*


class BottomSheetDialog : BottomSheetDialogFragment{
    constructor()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(com.mash.up.pixtus_app.R.layout.bottom_sheet_dialog, container, false)
        with(view){
            bottonSheet_workout.setOnClickListener{
                val nextIntent = Intent(context, WorkoutListActivity::class.java)
                startActivity(nextIntent)
            }
            bottonSheet_history.setOnClickListener{
                Toast.makeText(context, "서비스 준비중 입니다", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }
}