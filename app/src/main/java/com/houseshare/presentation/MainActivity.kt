package com.houseshare.presentation

import android.os.Bundle
import android.view.ViewGroup.MarginLayoutParams
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.*
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.houseshare.R
import com.houseshare.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    private val topLevelDestinationIds = setOf(
        R.id.shoppingFragment,
        R.id.cleaningFragment,
    )


    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()


    private lateinit var navViewToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        setupDrawerLayout()

        setupFab()

        setupNavigation()
    }

    private fun setupNavigation() {
        val navView = binding.navView
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragmentHouse) as NavHostFragment
        val navController = navHostFragment.navController

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds,
            binding.drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.currentDestination?.let {
            navView.setCheckedItem(it.id)
        }

        // TODO: for fab animation 
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
        }
    }


    private fun setupDrawerLayout() {
        navViewToggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.appBarMain.toolbar,
            R.string.open,
            R.string.close
        )
        binding.drawerLayout.addDrawerListener(navViewToggle)
        navViewToggle.syncState()
    }

    private fun setupFab() {
        val fab = binding.appBarMain.fab
        val fabInitialMarginBottom = binding.appBarMain.fab.marginBottom

        ViewCompat.setOnApplyWindowInsetsListener(fab) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

            view.updateLayoutParams<MarginLayoutParams> {
                bottomMargin = insets.bottom + fabInitialMarginBottom
            }

            // Return CONSUMED if you don't want want the window insets to keep being
            // passed down to descendant views.
            WindowInsetsCompat.CONSUMED
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navHostFragmentHouse)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}