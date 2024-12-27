package com.example.easychatgpt.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.easychatgpt.R

class HelppageScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_helppage_screen, container, false)

        val backBtn = view.findViewById<ImageView>(R.id.backImg)
        backBtn.setOnClickListener{
            findNavController().navigate(R.id.action_helppageScreenFragment_to_robotListScreenFragment)
        }

        // Set up click listeners for each number
        view.findViewById<TextView>(R.id.number1).setOnClickListener { dialNumber("+91-8376804102") }
        view.findViewById<TextView>(R.id.number2).setOnClickListener { dialNumber("7893078930") }
        view.findViewById<TextView>(R.id.number31).setOnClickListener { dialNumber("+91-8142020033") }
        view.findViewById<TextView>(R.id.number31).setOnClickListener { dialNumber("+91-8142020044") }
        view.findViewById<TextView>(R.id.number41).setOnClickListener { dialNumber("+91-8023655557") }
        view.findViewById<TextView>(R.id.number42).setOnClickListener { dialNumber("+91-8023656667") }
        view.findViewById<TextView>(R.id.number5).setOnClickListener { dialNumber("022-25521111") }
        view.findViewById<TextView>(R.id.number6).setOnClickListener { dialNumber("+91-7676602602") }

        return view
    }

    // Function to dial a number
    private fun dialNumber(number: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$number")
        startActivity(intent)
    }
}