package com.example.solutionchallenge

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class Register_Activity : AppCompatActivity() {



    lateinit var create_btn:Button
    lateinit var txtemail :EditText
    lateinit var txtpassword:EditText
    lateinit var txtname:EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        val firebaseUser: FirebaseUser? = auth.currentUser
        val userId= auth.uid



        create_btn = findViewById(R.id.btn_createAccount)
        txtemail = findViewById(R.id.txt_email)
        txtpassword = findViewById(R.id.txt_password)
        txtname = findViewById(R.id.txt_name)




        create_btn.setOnClickListener {

            val userEmail  = txtemail.text.toString().trim()
            val userPassword = txtpassword.text.toString().trim()
            val name = txtname.text.toString()

            auth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener{

                if (it.isSuccessful){
                  sendEmailVerification()
                    send_data_firebase()
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }else{
                    Toast.makeText(this ,"failed" + it.exception,Toast.LENGTH_SHORT).show()

                }
            }



        }
    }
    private fun sendEmailVerification(){
        val firebaseUser: FirebaseUser? = auth.currentUser
        firebaseUser.let {
            it?.sendEmailVerification()?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext,"email sent to ${txtemail.text.toString()}",Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    override fun onStart() {
        super.onStart()
        val user: FirebaseUser? = auth.currentUser
        Log.e("user", auth.currentUser.toString())
        user?.let {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
            Toast.makeText(applicationContext , "welcome back",Toast.LENGTH_SHORT).show()
        }
    }
    fun send_data_firebase(){
        val userId= auth.uid
        val name = txtname.text.toString()
        database.child("users").child(userId.toString()).child("email").setValue(txtemail.text.toString())
        database.child("users").child(userId.toString()).child("name").setValue(name)


    }

}