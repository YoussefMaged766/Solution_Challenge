package com.example.solutionchallenge

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import androidx.databinding.DataBindingUtil
import com.example.solutionchallenge.classes.Toast
import com.example.solutionchallenge.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso


class ProfileFragment : Fragment() {


    private val pickImage = 100
    private var imageUri: Uri? = null
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private val PREFS_NAME = "kotlincodes"
    lateinit var sharedPref: SharedPreferences

    lateinit var  binding: FragmentProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View {

         binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        database = FirebaseDatabase.getInstance().reference
       auth = FirebaseAuth.getInstance()



        sharedPref =requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    binding.floatingCameraBtn.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(requireContext(),android.Manifest.permission.READ_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_DENIED) {
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, UserProfile_Activity.PERMISSION_CODE)
                } else {
                    chooseImageGallery()

                }
            } else {
                chooseImageGallery()

            }

        }

       database.child("users").child(auth.uid.toString()).child("name")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    binding.txtnameProfile.text = snapshot.getValue().toString()

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
       binding.btnSaveProfile.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedPref.edit()
            editor.putString(PREFS_NAME, imageUri.toString())
            editor.apply()
        }



        var img = sharedPref.getString(PREFS_NAME, imageUri.toString())
//        Glide.with(getApplicationContext()).load(img.toString()).into(image_profile)
        Picasso.with(requireContext()).load(img.toString()).into(binding.imageProfile)












        set_dialog(binding.textviewAge.text.toString(), binding.textviewAgeEdit)
        set_dialog(binding.textviewTall.text.toString(), binding.textviewTallEdit)
        set_dialog(binding.textviewWeight.text.toString(), binding.textviewWeightEdit)
        set_dialog(binding.textviewAccount.text.toString(), binding.textviewAccountEdit)








        return binding.root

    }


    fun set_dialog(title: String, data: TextView) {

        data.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
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
            UserProfile_Activity.PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    chooseImageGallery()
                } else {
                    Toast(requireContext(),"Permission denied")
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            binding.imageProfile.setImageURI(imageUri)
            Log.e("image", imageUri.toString())

            val userId = auth.uid
            database.child("users").child(userId.toString()).child("imagelink")
                .setValue(imageUri.toString())

        }

    }
}