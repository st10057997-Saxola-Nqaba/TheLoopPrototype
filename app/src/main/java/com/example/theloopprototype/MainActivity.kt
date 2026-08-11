package com.example.theloopprototype

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.theloopprototype.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 1. Inflate your XML layout that contains the FragmentContainerView
        setContentView(R.layout.activity_main)

        // 2. Optional: If you ever need to access the NavController programmatically:
        // val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        // val navController = navHostFragment.navController
    }
}