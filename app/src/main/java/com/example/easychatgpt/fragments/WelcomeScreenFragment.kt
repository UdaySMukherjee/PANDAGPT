package com.example.easychatgpt.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.easychatgpt.R

class WelcomeScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_welcome_screen, container, false)

        // Load the GIF into the ImageView
        val gifImageView = view.findViewById<ImageView>(R.id.gifImageView)
        Glide.with(this)
            .asGif()
            .load(R.raw.panda) // Replace with your actual GIF file path in res/raw
            .into(gifImageView)

        val continueBtn = view.findViewById<Button>(R.id.continueBtn).apply {
//            startAnimation(
//                AnimationUtils.loadAnimation(view.context, R.anim.zoom_in_cut)
//            )
        }
        continueBtn.setOnClickListener {
            val action =
                WelcomeScreenFragmentDirections
                    .actionWelcomeScreenFragmentToRobotListScreenFragment()
            findNavController().navigate(action)
        }
        return view
    }

}