package com.example.solutionchallenge

import android.R.color
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.solutionchallenge.adapter.Nutration_adapter
import com.example.solutionchallenge.classes.Toast
import com.example.solutionchallenge.classes.meal_details
import com.example.solutionchallenge.databinding.ActivitySystemInfoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class SystemInfo_Fragment : Fragment() {
    lateinit var btn_submit: Button
    lateinit var spinner: Spinner
    lateinit var adapter1: Nutration_adapter
    lateinit var txt_tall: EditText
    lateinit var txt_weight: EditText
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    lateinit var data: meal_details
    lateinit var binding: ActivitySystemInfoBinding

//    val data =
//        listOf("1/ teaspoon ginger paste",
//            "\n2/ teaspoon red chilli powder",
//            "\n3/ teaspoon cumin powder salt as required",
//            "\n4/ cup hung curd",
//            "\n5/ teaspoon garlic paste",
//            "\n6/ teaspoon coriander powder",
//            "\n7/ teaspoon powdered black pepper",
//            "\n8/ teaspoon garam masala powder",
//            "\n9/ 350 gm chicken")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_system_info, container, false)
        database = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()
        val diseases = resources.getStringArray(R.array.spinner)
        val adapter = activity?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item, diseases
            )
        }
        adapter?.setDropDownViewResource(
            android.R.layout
                .simple_spinner_dropdown_item
        )
        binding.spinnerDisease.adapter = adapter
        data = meal_details()


//        adapter1 = Nutration_adapter(
//            arrayListOf(
//                Nutration_data("Side planks", R.drawable.istockphoto2, details_adapter(data .ex_data1),"Steps",0),
//                Nutration_data("Squats ", R.drawable.istockphoto4,details_adapter(data .ex_data2),"Steps",0),
//                Nutration_data("Push ups", R.drawable.istockphoto7,details_adapter(data .ex_data3),"Steps",0)
//
//            )
//        )
        try {


            binding.spinnerDisease.setOnItemSelectedListener(object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {

                    var item: String = binding.spinnerDisease.getSelectedItem().toString()
                    when(item){
                        "none"-> binding.txtItem.text = item
                        "Diabetes"-> binding.txtItem.text = item
                        "Hypertension"-> binding.txtItem.text = item
                        "Renal failure"-> binding.txtItem.text = item
                        "Hepatic failure"-> binding.txtItem.text = item

                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Toast(requireContext(), "error")
                }
            })
        } catch (e: Exception) {
            Toast(requireContext() , e.localizedMessage)

        }
        binding.btnSubmitSystem.setOnClickListener {

            try {
                var tall = Integer.parseInt(binding.tallEditSystem.text.toString())

                if (tall in 0..49){
                    Toast(requireContext() , "Invalid Tall")

                }
                else{
                    var bundle = Bundle()
                    bundle.putInt("system_result", tall)
                    bundle.putString("spinner", binding.txtItem.text.toString())

                    Log.e("onCreateView:1 ", bundle.toString())
                    it.findNavController().navigate(R.id.systemResult_Activity, bundle)

                    database.child("users").child(auth.uid.toString()).child("tall")
                        .setValue(binding.tallEditSystem.text.toString())
                    database.child("users").child(auth.uid.toString()).child("weight")
                        .setValue(binding.weightEditSystem.text.toString())
                    database.child("users").child(auth.uid.toString()).child("age")
                        .setValue(binding.ageEditSystem.text.toString())
                }





            }catch (e:Exception){
                checkdata()
                Toast(requireContext() , e.localizedMessage)

            }


        }

        return binding.root
    }

    private fun checkdata() {
        if (isEmpty(binding.tallEditSystem.toString())){
            binding.tallEditSystem.error = "Enter your Tall"
        }
         if (isEmpty(binding.weightEditSystem.toString())){
            binding.weightEditSystem.error = "Enter your Weight"

        }
        if (isEmpty(binding.ageEditSystem.toString())){
            binding.ageEditSystem.error = "Enter your Age"
        }
    }
//    initialize()
//
//
//
//
//
//    data = meal_details()
//
//    adapter1 = Nutration_adapter(
//    arrayListOf(
//    Nutration_data("Side planks", R.drawable.istockphoto2, details_adapter(data .ex_data1),"Steps",0),
//    Nutration_data("Squats ", R.drawable.istockphoto4,details_adapter(data .ex_data2),"Steps",0),
//    Nutration_data("Push ups", R.drawable.istockphoto7,details_adapter(data .ex_data3),"Steps",0)
//
//    )
//    )
//
//    btn_submit.setOnClickListener
//    {
//
//
////            var i = Intent(this, SystemResult_Activity::class.java)
//        var tall = Integer.parseInt(txt_tall.text.toString())
////            i.putExtra("tall", tall)
////            startActivity(i)
////            finish()
//        var bundle = Bundle()
//        bundle.putInt("system_result", tall)
//        it.findNavController().navigate(R.id.systemResult_Activity, bundle)
//
//        database.child("users").child(auth.uid.toString()).child("tall")
//            .setValue(txt_tall.text.toString())
//        database.child("users").child(auth.uid.toString()).child("weight")
//            .setValue(txt_weight.text.toString())
//
//    }
//
//
//}
//
//fun initialize() {
//    spinner = findViewById(R.id.spinner_disease)
//    btn_submit = findViewById(R.id.btn_submit_system)
//    txt_tall = findViewById(R.id.tall_edit_system)
//    txt_weight = findViewById(R.id.weight_edit_system)
//

}
