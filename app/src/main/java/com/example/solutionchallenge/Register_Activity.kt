package com.example.solutionchallenge

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.solutionchallenge.classes.Toast
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.lang.Exception


class Register_Activity : AppCompatActivity() {



    lateinit var create_btn:Button
    lateinit var txtemail :EditText
    lateinit var txtpassword:EditText
    lateinit var txtname:EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    lateinit var txtemail_container :TextInputLayout
    lateinit var txtpassword_container:TextInputLayout
    lateinit var txtname_container:TextInputLayout
    lateinit var txtSkip :TextView
    lateinit var txtlogin :TextView


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
        txtemail_container = findViewById(R.id.tct_container_email)
        txtname_container = findViewById(R.id.txt_container_name)
        txtpassword_container = findViewById(R.id.txt_container_password)
        txtSkip = findViewById(R.id.txt_skip)
        txtlogin = findViewById(R.id.txt_login)





        create_btn.setOnClickListener {

            val userEmail  = txtemail.text.toString().trim()
            val userPassword = txtpassword.text.toString().trim()
            val name = txtname.text.toString()
            try {
                auth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener{

                    if (it.isSuccessful){
                        sendEmailVerification()
                        send_data_firebase()
                        startActivity(Intent(this, Sign_In_Activity::class.java))
                        finish()
                    }else{
                        Toast(this ,"failed" + it.exception)


                    }
                }
            }catch (e:Exception){
                check_data()
            }





        }

        txtSkip.setOnClickListener {
            startActivity(Intent(this , Home_Activity::class.java))
        }
        txtlogin.setOnClickListener {
            startActivity(Intent(this , Sign_In_Activity::class.java))
        }






    }
    private fun sendEmailVerification(){
        val firebaseUser: FirebaseUser? = auth.currentUser
        firebaseUser.let {
            it?.sendEmailVerification()?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast(applicationContext,"email sent to ${txtemail.text.toString()}")
                }
            }
        }
    }
    override fun onStart() {
        super.onStart()
        val user: FirebaseUser? = auth.currentUser
        Log.e("user", auth.currentUser.toString())
        if (user!=null){
            user?.let {
                startActivity(Intent(this, Home_Activity::class.java))
                finish()
                Toast(applicationContext , "welcome back")
            }
        }else{
            Toast(this , "user isn't sign in")
        }

    }
    fun send_data_firebase(){
        val userId= auth.uid
        val name = txtname.text.toString()
        database.child("users").child(userId.toString()).child("email").setValue(txtemail.text.toString())
        database.child("users").child(userId.toString()).child("name").setValue(name)
        database.child("users").child(userId.toString()).child("start_date").setValue("0")


    }

    fun isEmpty(text: EditText): Boolean {
        val str: CharSequence = text.text.toString()
        return TextUtils.isEmpty(str)
    }
    fun isEmail(text: EditText): Boolean {
        val email: CharSequence = text.text.toString()
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun check_data(){
        if (isEmpty(txtemail)){
            txtemail_container.boxStrokeColor = getColor(R.color.red)
            txtemail.setError("Enter your Email")
        }
        if (isEmpty(txtpassword)){
            txtpassword_container.boxStrokeColor = getColor(R.color.red)
            txtpassword.setError("Enter password")
        }
        if (isEmpty(txtname)){
            txtname_container.boxStrokeColor = getColor(R.color.red)
            txtname.setError("Enter your name")
        }
        if (isEmail(txtemail) ==false){
            txtemail.setError("Enter valid Email")
        }
    }

}