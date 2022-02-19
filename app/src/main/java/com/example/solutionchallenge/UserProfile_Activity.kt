package com.example.solutionchallenge

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class UserProfile_Activity : AppCompatActivity() {
    lateinit var btn_camera:FloatingActionButton
    lateinit var image_profile :ImageView
    private val pickImage = 100
    private var imageUri: Uri? = null
    private lateinit var database: DatabaseReference

    lateinit var txtage:TextView
    lateinit var txttall:TextView
    lateinit var txtweight:TextView
    lateinit var txtaccount:TextView
    lateinit var txtage_edit:TextView
    lateinit var txttall_edit:TextView
    lateinit var txtweight_edit:TextView
    lateinit var txtaccount_edit:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        initalize()

        btn_camera.setOnClickListener{

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, PERMISSION_CODE)
                } else{
                    chooseImageGallery();

                }
            }else{
                chooseImageGallery();

            }

        }
        set_dialog(txtage.text.toString() , txtage_edit)
        set_dialog(txttall.text.toString() , txttall_edit)
        set_dialog(txtweight.text.toString() , txtweight_edit)
        set_dialog(txtaccount.text.toString() , txtaccount_edit)




    }

    fun initalize(){
        btn_camera = findViewById(R.id.floating_camera_btn)
        image_profile = findViewById(R.id.image_profile)

        txtage = findViewById(R.id.textview_age)
        txttall = findViewById(R.id.textview_tall)
        txtweight = findViewById(R.id.textview_weight)
        txtaccount = findViewById(R.id.textview_account)

        txtage_edit = findViewById(R.id.textview_age_edit)
        txtweight_edit = findViewById(R.id.textview_weight_edit)
        txttall_edit = findViewById(R.id.textview_tall_edit)
        txtaccount_edit = findViewById(R.id.textview_account_edit)

        database = FirebaseDatabase.getInstance().reference
        database.child("users").child("null").child("email").setValue(imageUri)

    }

    fun set_dialog( title:String , data :TextView){

        data.setOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle(title)
            val customlayout:View = layoutInflater.inflate(R.layout.custom_dialog,null)
            builder.setView(customlayout)
            builder.setPositiveButton("OK" ,  object :DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    var edittext:EditText = customlayout.findViewById(R.id.editText)
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
        private val PERMISSION_CODE = 1001

    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    chooseImageGallery()
                }else{
                    Toast.makeText(this,"Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            image_profile.setImageURI(imageUri)
            Log.e("image" , imageUri.toString())

        }


    }


}