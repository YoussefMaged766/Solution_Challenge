package com.example.solutionchallenge

import android.Manifest
import android.content.Context

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences

import android.content.pm.PackageManager
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.GenericTransitionOptions.with
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.with
import com.example.solutionchallenge.classes.sharedPrefrence
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso


class UserProfile_Activity : AppCompatActivity() {
    lateinit var btn_camera: FloatingActionButton
    lateinit var btn_back: FloatingActionButton
    lateinit var image_profile: ImageView
    private val pickImage = 100
    private var imageUri: Uri? = null
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    lateinit var txtname: TextView
    lateinit var txtage: TextView
    lateinit var txttall: TextView
    lateinit var txtweight: TextView
    lateinit var txtaccount: TextView
    lateinit var txtage_edit: TextView
    lateinit var txttall_edit: TextView
    lateinit var txtweight_edit: TextView
    lateinit var txtaccount_edit: TextView
    lateinit var btn_save: Button
    private val PREFS_NAME = "kotlincodes"
    lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        initalize()
        val sharedPreference: sharedPrefrence = sharedPrefrence(this)
        sharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        btn_camera.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, PERMISSION_CODE)
                } else {
                    chooseImageGallery();

                }
            } else {
                chooseImageGallery();

            }

        }
        set_dialog(txtage.text.toString(), txtage_edit)
        set_dialog(txttall.text.toString(), txttall_edit)
        set_dialog(txtweight.text.toString(), txtweight_edit)
        set_dialog(txtaccount.text.toString(), txtaccount_edit)

//        database.child("users").child(auth.uid.toString()).child("imagelink").addListenerForSingleValueEvent(object :
//            ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val value = snapshot.getValue()
//                Glide.with(getApplicationContext()).load(value.toString()).into(image_profile)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//
//        })


        database.child("users").child(auth.uid.toString()).child("name")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    txtname.text = snapshot.getValue().toString()

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        btn_save.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedPref.edit()
            editor.putString(PREFS_NAME, imageUri.toString())
            editor.apply()
        }
        btn_back.setOnClickListener {
            startActivity(Intent(applicationContext, HomeActivity::class.java))
        }


        var img = sharedPref.getString(PREFS_NAME, imageUri.toString())
//        Glide.with(getApplicationContext()).load(img.toString()).into(image_profile)
        Picasso.with(this).load(img.toString()).into(image_profile)

    }





    fun initalize() {
        btn_camera = findViewById(R.id.floating_camera_btn)
        btn_back = findViewById(R.id.floating_back_btn)
        image_profile = findViewById(R.id.image_profile)

        txtname = findViewById(R.id.txtname_profile)
        txtage = findViewById(R.id.textview_age)
        txttall = findViewById(R.id.textview_tall)
        txtweight = findViewById(R.id.textview_weight)
        txtaccount = findViewById(R.id.textview_account)

        txtage_edit = findViewById(R.id.textview_age_edit)
        txtweight_edit = findViewById(R.id.textview_weight_edit)
        txttall_edit = findViewById(R.id.textview_tall_edit)
        txtaccount_edit = findViewById(R.id.textview_account_edit)
        btn_save = findViewById(R.id.btn_save_profile)

        database = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()


    }

    fun set_dialog(title: String, data: TextView) {

        data.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle(title)
            val customlayout: View = layoutInflater.inflate(R.layout.custom_dialog, null)
            builder.setView(customlayout)
            builder.setPositiveButton("OK", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    var edittext: EditText = customlayout.findViewById(R.id.editText)
                    data.text = edittext.text.toString()
                }

            })
            val dialog = builder.create()
            dialog.show()
        }

    }

    private fun chooseImageGallery() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, pickImage)

    }

    companion object {
        private val IMAGE_CHOOSE = 1000;
        val PERMISSION_CODE = 1001

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    chooseImageGallery()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            image_profile.setImageURI(imageUri)
            Log.e("image", imageUri.toString())

//            val userId = auth.uid
//            database.child("users").child(userId.toString()).child("imagelink")
//                .setValue(imageUri.toString())

        }

    }


}