package com.example.solutionchallenge

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.solutionchallenge.classes.Toast
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class custom_dialog : Fragment() {

    lateinit var txtemail: EditText
    lateinit var txtpassword: EditText
    lateinit var btn_remve: Button
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var v = inflater.inflate(R.layout.fragment_custom_dialog, container, false)

        btn_remve = v.findViewById(R.id.btn_remove)
        txtemail = v.findViewById(R.id.editText_email)
        txtpassword = v.findViewById(R.id.editText_password)
        database = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()

        btn_remve.setOnClickListener {
            val user = FirebaseAuth.getInstance().currentUser

            // Get auth credentials from the user for re-authentication. The example below shows
            // email and password credentials but there are multiple possible providers,
            // such as GoogleAuthProvider or FacebookAuthProvider.
            val credential = EmailAuthProvider.getCredential(
                txtemail.text.toString().trim(),
                txtpassword.text.toString().trim()
            )
            database.child("users").child(auth.uid.toString()).removeValue()
            // Prompt the user to re-provide their sign-in credentials
            user?.reauthenticate(credential)?.addOnCompleteListener {
                user.delete()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(requireContext(), Sign_In_Activity::class.java))


                            Toast(requireContext(), "Deleted User Successfully,")
                        }
                    }
            }


        }






        return v
    }

    private fun deleteuser(email: String, password: String) {
        val user = FirebaseAuth.getInstance().currentUser

        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        val credential = EmailAuthProvider.getCredential(email, password)

        // Prompt the user to re-provide their sign-in credentials
        user?.reauthenticate(credential)?.addOnCompleteListener {
            user.delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(requireContext(), Sign_In_Activity::class.java))
                        Toast(requireContext(), "Deleted User Successfully,")
                    }
                }
        }
    }

}