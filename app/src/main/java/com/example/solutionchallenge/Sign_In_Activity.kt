package com.example.solutionchallenge

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.text.TextUtils.isEmpty
import android.util.Patterns
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.solutionchallenge.classes.Toast
import com.example.solutionchallenge.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class Sign_In_Activity : AppCompatActivity() {
    lateinit var  binding :ActivitySignInBinding
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    lateinit var txtemail:String
    lateinit var txtpassword:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()


        binding.btnCreate.setOnClickListener {
            startActivity(Intent(this , Register_Activity::class.java))
        }

        binding.loginBtn.setOnClickListener {
            txtemail = binding.txtEmailSign.text.toString().trim()
            txtpassword = binding.txtPasswordSign.text.toString().trim()
            try {
                auth.signInWithEmailAndPassword(txtemail,txtpassword).addOnCompleteListener {
                    if (it.isSuccessful){
                        startActivity(Intent(this, Home_Activity::class.java))
                        finish()
                        Toast(this , "Login success")
                    }
                    else{
                        Toast(this , "failed" + it.exception)
                    }

                }
            }catch (e:Exception){
                check_data()
                Toast(this , e.localizedMessage)
            }


        }

//        auth.addAuthStateListener {
//            val user = FirebaseAuth.getInstance().currentUser
//            if (user != null) {
//                val intent = Intent(this, Home_Activity::class.java)
//                startActivity(intent)
//                finish()
//            }
//        }
        binding.txtForgotPass.setOnClickListener {
            auth.sendPasswordResetEmail(binding.txtEmailSign.text.toString()).addOnCompleteListener {
                if (it.isSuccessful){
                    Toast(this , "sent password reset")
                }
            }
        }

    }
    fun check_data(){
        if (isEmpty(binding.txtEmailSign.toString())){
          binding.txtContainerEmail.boxStrokeColor = getColor(R.color.red)
            binding.txtEmailSign.error = "Enter your Email"
        }
        if (isEmpty(binding.txtPasswordSign.toString())){
            binding.txtContainerPassword.boxStrokeColor = getColor(R.color.red)
            binding.txtPasswordSign.setError("Enter password")
        }

        if (isEmail(binding.txtEmailSign) ==false){
            binding.txtEmailSign.setError("Enter valid Email")
        }
    }
    fun isEmail(text: EditText): Boolean {
        val email: CharSequence = text.text.toString()
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}