package com.mash.up.pixtus_app.ui.view.Dialog

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
                Toast.makeText(context, "Hi", Toast.LENGTH_SHORT).show()
            }
            bottonSheet_history.setOnClickListener{
                Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

}