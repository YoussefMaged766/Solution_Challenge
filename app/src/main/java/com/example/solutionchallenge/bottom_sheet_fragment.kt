package com.example.solutionchallenge


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.example.solutionchallenge.adapter.details_adapter
import com.example.solutionchallenge.classes.Nutration_data

import com.example.solutionchallenge.databinding.FragmentBottomSheetFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class bottom_sheet_fragment : BottomSheetDialogFragment() {

    lateinit var binding: FragmentBottomSheetFragmentBinding
    lateinit var bottomSheetDialog: BottomSheetDialog
    lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    lateinit var user: Nutration_data
    lateinit var adapter: details_adapter
    val data =
        listOf(
            "1/ teaspoon ginger paste",
            "\n2/ teaspoon red chilli powder",
            "\n3/ teaspoon cumin powder salt as required",
            "\n4/ cup hung curd",
            "\n5/ teaspoon garlic paste",
            "\n6/ teaspoon coriander powder",
            "\n7/ teaspoon powdered black pepper",
            "\n8/ teaspoon garam masala powder",
            "\n9/ 350 gm chicken"
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_bottom_sheet_fragment,
            container,
            false
        )
        val dialog = dialog as BottomSheetDialog
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        binding.btnCollabse.setOnClickListener {
            dialog.behavior.state = BottomSheetBehavior.STATE_HIDDEN
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user = arguments?.getSerializable("item_sheet") as Nutration_data

//        adapter = details_adapter(data)
//
//        binding.recyclerDetails.adapter = adapter

        binding.txtNameSheet.text = user.name
        binding.itemImgSheet.setImageResource(user.img)
        binding.recyclerDetails.adapter = user.details
        binding.txtIntegrate.text = user.title
        binding.txtTime.text = user.time
        binding.imgTime.setImageResource(user.img_time)
        binding.txtDirection.text = user.txt_direction
        binding.txt18.text = user.dir


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }


}