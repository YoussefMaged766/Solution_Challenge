package com.example.solutionchallenge.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.solutionchallenge.R
import com.example.solutionchallenge.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment() {

lateinit var text:TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view :View=inflater.inflate(R.layout.fragment_gallery,container,false)

        text = view.findViewById(R.id.text_gallery)
        text.text = "gallery"



        return view
    }

}