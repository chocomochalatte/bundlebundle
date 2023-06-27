package com.example.bundlebundle

import android.os.Bundle
import android.view.Menu
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.bundlebundle.databinding.ActivityBasicBinding

class BasicActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityBasicBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBasicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarBasic.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_basic)

        val topLevelDestinations = setTopLevelMainFragment()
        appBarConfiguration = createAppBarConfiguration(topLevelDestinations, binding.drawerLayout)

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    protected open fun setTopLevelMainFragment(): Set<Int> {
        return setOf(
            R.id.nav_home
        )
    }

    private fun createAppBarConfiguration(topLevelDestinations: Set<Int>, drawerLayout: DrawerLayout): AppBarConfiguration {
        return AppBarConfiguration.Builder(topLevelDestinations)
            .setOpenableLayout(drawerLayout)
            .build()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.basic, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_basic)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun onCloseDrawer(view: View) {
        binding.drawerLayout.closeDrawers()
    }
}