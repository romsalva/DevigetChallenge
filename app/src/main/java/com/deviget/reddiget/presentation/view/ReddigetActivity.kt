package com.deviget.reddiget.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.deviget.reddiget.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * Entry point. Can handle a single or dual NavHostFragments
 */
@AndroidEntryPoint
class ReddigetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reddiget)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        //Not sure why, but we need to initialize both the action bar AND the toolbar. Even though they should be the same.
        //We could probably get away with only the toolbar, but I'll try that some other time.
        setupActionBarWithNavController(
            navController,
            appBarConfiguration
        )
        toolbar.setupWithNavController(
            navController,
            appBarConfiguration
        )
        //Move "detail" fragment to the secondary NavHost if there was one at the top
        if (resources.getBoolean(R.bool.side_by_side)) {
            val currentBackStackEntry = navController.currentBackStackEntry
            if (currentBackStackEntry != null && currentBackStackEntry.destination.label == getString(R.string.nav_label_post)) {
                val postNavController = (supportFragmentManager.findFragmentById(R.id.fragment_container_post) as NavHostFragment).navController
                //Clear backstack in case there was a remaining fragment from a previous rotation.
                postNavController.popBackStack(R.id.destination_post, true)
                postNavController.navigate(R.id.destination_post, currentBackStackEntry.arguments)
                navController.popBackStack()
            }
        }
    }

}
