package com.example.solutionchallenge

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.bumptech.glide.Glide
import com.example.solutionchallenge.databinding.ActivityHome2Binding
import com.example.solutionchallenge.databinding.NavHeaderHomeBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class Home_Activity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHome2Binding

    //     lateinit var binding2: NavHeaderHomeBinding
    lateinit var drawerLayout: DrawerLayout
    lateinit var header: LinearLayout
    lateinit var img_profile: ImageView
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHome2Binding.inflate(layoutInflater)
//        binding2 = NavHeaderHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarHome.toolbar)
        database = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()
        binding.appBarHome.toolbar.getNavigationIcon()
            ?.setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
//        header = binding2.headerContainer


        val header: View = binding.navView.getHeaderView(0)
        img_profile = header.findViewById(R.id.imageView_profile_header)
        drawerLayout = binding.drawerLayout

        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_dark, R.id.nav_Diet, R.id.nav_Exit
            ), drawerLayout
        )
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)


        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        binding.navViewBot.setupWithNavController(navController)




        database.child("users").child(auth.uid.toString()).child("imagelink")
            .addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val value = snapshot.getValue()
                    Glide.with(getApplicationContext()).load(value.toString())
                        .into(img_profile)
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })


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