package com.taras_bekhta.monesetest.view

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.View
import com.taras_bekhta.monesetest.R

abstract class BaseFragment: Fragment() {
    abstract fun getTitle(): String

    lateinit var parentActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        parentActivity = activity!! as MainActivity
    }

    override fun onResume() {
        super.onResume()
        parentActivity.supportActionBar!!.title = getTitle()
    }

    fun showErrorSnackBar(msg: String, view: View) {
        val snackBar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
        snackBar.view.setBackgroundColor(ContextCompat.getColor(context!!, R.color.colorError))
        snackBar.show()
    }

    fun showSnackBar(msg: String, view: View) {
        val snackBar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
        snackBar.view.setBackgroundColor(ContextCompat.getColor(context!!, R.color.green))
        snackBar.show()
    }

}