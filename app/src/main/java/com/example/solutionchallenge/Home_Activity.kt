package com.example.solutionchallenge

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.bumptech.glide.Glide
import com.example.solutionchallenge.classes.Toast
import com.example.solutionchallenge.databinding.ActivityHome2Binding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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
    private val PREFS_NAME = "kotlincodes"
    lateinit var sharedPref: SharedPreferences


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
        var navController = findNavController(R.id.nav_host_fragment_content_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_dark, R.id.nav_Diet, R.id.nav_Exit
            ), drawerLayout
        )
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)







        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
        binding.navViewBot.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener { it: MenuItem ->
            if (it.itemId == R.id.nav_Diet) {
               database.child("users").child(auth.uid.toString()).child("start_date").addListenerForSingleValueEvent(object :ValueEventListener{
                   override fun onDataChange(snapshot: DataSnapshot) {
                       if (snapshot.value.toString().equals("0")){
                           Toast(this@Home_Activity , "No plan picked")
                       }else{
                           navView.setupWithNavController(navController)
                       }
                   }

                   override fun onCancelled(error: DatabaseError) {
                       TODO("Not yet implemented")
                   }

               })

            }
            if (it.itemId == R.id.nav_Exit){
//                navController.navigate(R.id.nav_Exit)
//                 navView.setupWithNavController(navController)
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setTitle("Log out")
                val customlayout: View = layoutInflater.inflate(R.layout.custom_dialog_exit, null)
                builder.setView(customlayout)
                builder.setPositiveButton("delete", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        var edittext_email1: EditText = customlayout.findViewById(R.id.editText_email)
                        var edittext_password1: EditText = customlayout.findViewById(R.id.editText_password)
                        var email = edittext_email1.text.toString()
                        var password = edittext_password1.text.toString()
                        val user = FirebaseAuth.getInstance().currentUser
                        val credential = EmailAuthProvider.getCredential(email, password)
                        database.child("users").child(auth.uid.toString()).removeValue()
                        sharedPref =getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                        sharedPref.edit().remove(PREFS_NAME).apply()
                        // Prompt the user to re-provide their sign-in credentials
                        user?.reauthenticate(credential)?.addOnCompleteListener {
                            user.delete()
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        startActivity(Intent(this@Home_Activity, Sign_In_Activity::class.java))
//                                        navView.setupWithNavController(navController)
                                            finish()

                                        Toast(this@Home_Activity, "Deleted User Successfully,")
                                    }
                                }
                        }

                    }

                })
                val dialog = builder.create()
                dialog.show()



            }



            false


        }





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

    fun set_dialog(title: String, email: String, password: String) {


        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        val customlayout: View = layoutInflater.inflate(R.layout.custom_dialog_exit, null)
        builder.setView(customlayout)
        builder.setPositiveButton("delete", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                var edittext_email1: EditText = customlayout.findViewById(R.id.editText_email)
                var edittext_password1: EditText = customlayout.findViewById(R.id.editText_password)
                var email = edittext_email1.text.toString()
                var password = edittext_password1.text.toString()


            }

        })
        val dialog = builder.create()
        dialog.show()


    }

    fun clickon_diet() {


    }


}


