package com.example.easychatgpt.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.easychatgpt.R
import com.example.easychatgpt.utils.gone

class SelectTextScreenFragment : Fragment() {
    private val selectArgs : SelectTextScreenFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_select_text_screen, container, false)

        val toolbarView = view.findViewById<View>(R.id.toolbarLayout)

        val robotImageLL = toolbarView.findViewById<View>(R.id.robotImageLL)
        robotImageLL.gone()

        val closeImage = toolbarView.findViewById<ImageView>(R.id.backImg)
        closeImage.setImageResource(R.drawable.ic_close)
        closeImage.setOnClickListener(){
            findNavController().navigateUp()
        }

        val titleTxt = toolbarView.findViewById<TextView>(R.id.titleTxt)
        titleTxt.text = "Select Text"

        val selectTxt = toolbarView.findViewById<TextView>(R.id.selectText)
        selectTxt.text = selectArgs.selectedMessage

        return view
    }

}