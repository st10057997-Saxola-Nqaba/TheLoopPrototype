package com.example.theloopprototype

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val ahtDestinations = setOf(
        R.id.ahtHomeFragment, R.id.ownerSearchFragment, R.id.myProfileFragment
    )
    private val ownerDestinations = setOf(
        R.id.petOwnerHomeFragment, R.id.myRequestsFragment, R.id.petOwnerProfileFragment
    )

    private var currentMenuRes: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        // Single listener works for ANY role, since menu item IDs
        // always match fragment IDs in nav_graph.xml directly.
        bottomNavigationView.setOnItemSelectedListener { item ->
            navController.navigate(item.itemId)
            true
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when {
                ahtDestinations.contains(destination.id) -> {
                    setMenuIfNeeded(bottomNavigationView, R.menu.bottom_nav_menu) // your existing AHT menu file
                    bottomNavigationView.visibility = View.VISIBLE
                }
                ownerDestinations.contains(destination.id) -> {
                    setMenuIfNeeded(bottomNavigationView, R.menu.bottom_nav_menu_owner)
                    bottomNavigationView.visibility = View.VISIBLE
                }
                else -> {
                    // Login, Admin, and every other detail/sub-screen: no bottom nav
                    bottomNavigationView.visibility = View.GONE
                }
            }
        }
    }

    private fun setMenuIfNeeded(bottomNav: BottomNavigationView, menuRes: Int) {
        if (currentMenuRes != menuRes) {
            bottomNav.menu.clear()
            bottomNav.inflateMenu(menuRes)
            currentMenuRes = menuRes
        }
    }
}