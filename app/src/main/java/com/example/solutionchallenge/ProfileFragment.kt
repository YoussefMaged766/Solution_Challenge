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
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.solutionchallenge.classes.Toast
import com.example.solutionchallenge.databinding.FragmentProfileBinding
import com.google.android.material.snackbar.Snackbar
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


    lateinit var binding: FragmentProfileBinding
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


        var frg: Fragment = requireActivity().getSupportFragmentManager()
            .findFragmentById(R.id.nav_host_fragment_content_home)!!
        val ft: FragmentTransaction =
            requireActivity().getSupportFragmentManager().beginTransaction()
        ft.detach(frg)
        ft.attach(frg)
        ft.commit()



        sharedPref = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        binding.floatingCameraBtn.setOnClickListener {

            if (auth.uid != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(
                            requireContext(),
                            android.Manifest.permission.READ_EXTERNAL_STORAGE
                        ) == PermissionChecker.PERMISSION_DENIED
                    ) {
                        val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                        requestPermissions(permissions, PERMISSION_CODE)
                    } else {
                        chooseImageGallery()

                    }
                } else {
                    chooseImageGallery()

                }
            } else {
                Snackbar.make(
                    binding.profileLayout,
                    "you should sign in first",
                    Snackbar.LENGTH_LONG
                )
                    .setAction("Sign up") {
                        startActivity(Intent(requireContext(), Register_Activity::class.java))

                    }
                    .show()

            }


        }

        database.child("users").child(auth.uid.toString()).child("name")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        binding.txtnameProfile.text = snapshot.getValue().toString()
                    } else {
                        binding.txtnameProfile.text = "guest"
                    }


                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        database.child("users").child(auth.uid.toString()).child("age")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    binding.textviewAgeEdit.text = snapshot.value.toString()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        binding.btnSaveProfile.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedPref.edit()
            editor.putString(PREFS_NAME, imageUri.toString())
            editor.apply()
            database.child("users").child(auth.uid.toString()).child("tall")
                .setValue(binding.textviewTallEdit.text.toString())
            database.child("users").child(auth.uid.toString()).child("weight")
                .setValue(binding.textviewWeightEdit.text.toString())
            database.child("users").child(auth.uid.toString()).child("age")
                .setValue(binding.textviewAgeEdit.text.toString())
        }



//        Glide.with(getApplicationContext()).load(img.toString()).into(image_profile)

        var img = sharedPref.getString(PREFS_NAME, imageUri.toString())
        if (sharedPref.contains(PREFS_NAME)) {
            Picasso.with(requireContext()).load(img.toString()).into(binding.imageProfile)
        } else {
            binding.imageProfile.setImageResource(R.drawable.profile_icon)
        }



        set_dialog(binding.textviewAge.text.toString(), binding.textviewAgeEdit)
        set_dialog(binding.textviewTall.text.toString(), binding.textviewTallEdit)
        set_dialog(binding.textviewWeight.text.toString(), binding.textviewWeightEdit)
        set_dialog(binding.textviewAccount.text.toString(), binding.textviewAccountEdit)



        check_data()


setHasOptionsMenu(true)








        return binding.root

    }

    override fun onStart() {
        super.onStart()
        if (binding.imageProfile.drawable == null){
            binding.imageProfile.setImageResource(R.drawable.ic_baseline_person_pin_24)
            Log.e( "onResume: ","start" )
        }
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.optional_menu,menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.remove -> {
                val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Remove profile photo?")
                builder.setPositiveButton("REMOVE" , object :DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        database.child("users").child(auth.uid.toString()).child("imagelink").removeValue()
                        sharedPref = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                        sharedPref.edit().remove(PREFS_NAME).apply()
                        binding.imageProfile.setImageResource(R.drawable.profile_icon)
                        val i = Intent(requireActivity(), Home_Activity::class.java)
                        activity?.finish()
                        activity?. overridePendingTransition(0, 0)
                        startActivity(i)
                        activity?.overridePendingTransition(0, 0)

                    }

                })
                builder.setNegativeButton("CANCEL", object :DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {

                    }

                })

                val dialog = builder.create()
                dialog.show()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    chooseImageGallery()
                } else {
                    Toast(requireContext(), "Permission denied")
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

    fun check_data() {
        if (auth.uid == null) {
            binding.textviewAgeEdit.text = ""
            binding.textviewTallEdit.text = ""
            binding.textviewWeightEdit.text = ""
            binding.textviewAccountEdit.text = ""
        }
        if (auth.uid != null) {
            database.child("users").child(auth.uid.toString()).child("tall")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        binding.textviewTallEdit.text = snapshot.value.toString()
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })
            database.child("users").child(auth.uid.toString()).child("weight")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        binding.textviewWeightEdit.text = snapshot.value.toString()
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })
            database.child("users").child(auth.uid.toString()).child("email")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        binding.textviewAccountEdit.text = snapshot.value.toString()
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })
            database.child("users").child(auth.uid.toString()).child("age")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        binding.textviewAgeEdit.text = snapshot.value.toString()
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })
        } else {
            database.child("users").child(auth.uid.toString()).child("start_date")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.value.toString().equals("0")) {
                            binding.textviewAgeEdit.text = ""
                            binding.textviewTallEdit.text = ""
                            binding.textviewWeightEdit.text = ""
                            database.child("users").child(auth.uid.toString()).child("email")
                                .addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        binding.textviewAccountEdit.text = snapshot.value.toString()
                                    }

                                    override fun onCancelled(error: DatabaseError) {

                                    }

                                })
                        }

                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })
        }
    }
}