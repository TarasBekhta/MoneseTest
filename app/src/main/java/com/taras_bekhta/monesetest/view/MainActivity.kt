package com.taras_bekhta.monesetest.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.taras_bekhta.monesetest.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val host = supportFragmentManager.findFragmentById(R.id.navigation_fragment_main) as NavHostFragment
        NavigationUI.setupActionBarWithNavController(this, host.navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        NavigationUI.navigateUp(Navigation.findNavController(this, R.id.navigation_fragment_main), null)
        return true
    }

    override fun onBackPressed() {
        NavigationUI.navigateUp(Navigation.findNavController(this, R.id.navigation_fragment_main), null)
    }
}
