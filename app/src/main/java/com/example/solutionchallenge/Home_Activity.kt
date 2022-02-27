package com.example.solutionchallenge

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.example.solutionchallenge.databinding.ActivityHome2Binding
import com.example.solutionchallenge.ui.home.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class Home_Activity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHome2Binding
    lateinit var drawerLayout: DrawerLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHome2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarHome.toolbar)
        binding.appBarHome.toolbar.getNavigationIcon()?.setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);



         drawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_dark, R.id.nav_Diet,R.id.nav_Exit
            ), drawerLayout
        )
        NavigationUI.setupActionBarWithNavController(this,navController,drawerLayout)


        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
      binding.navViewBot.setupWithNavController(navController)

//        binding.navViewBot.setOnNavigationItemSelectedListener(navigationItemSelectedListener)
    }

//    var navigationItemSelectedListener =
//        BottomNavigationView.OnNavigationItemSelectedListener { item ->
//
//
//            when (item.itemId) {
//
//                 R.id.nav_Exercise ->findNavController(R.id.nav_host_fragment_content_home).navigate(R.id.nav_Exercise)
//                 R.id.nav_meal -> findNavController(R.id.nav_host_fragment_content_home).navigate(R.id.nav_meal)
//                R.id.nav_profile -> findNavController(R.id.nav_host_fragment_content_home).navigate(R.id.nav_profile)
//                R.id.nav_home -> findNavController(R.id.nav_host_fragment_content_home).navigate(R.id.nav_home)
//
//        }
//
//            true
//        }



    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}