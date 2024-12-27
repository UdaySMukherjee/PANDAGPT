package com.example.easychatgpt.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.easychatgpt.R

class RobotListScreenFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_robotlist_screen, container, false)

        val moveChatBtn = view.findViewById<Button>(R.id.moveChatbtn)
        moveChatBtn.setOnClickListener{
            val action =
                RobotListScreenFragmentDirections
                    .actionRobotListScreenFragmentToChatScreenFragment()
            findNavController().navigate(action)
        }

        val helplineBtn = view.findViewById<ImageView>(R.id.helpLineButton)
        helplineBtn.setOnClickListener{
            findNavController().navigate(R.id.action_robotListScreenFragment_to_helplineScreenFragment)
        }

        val reminderBtn = view.findViewById<ImageView>(R.id.calendarButton)
        reminderBtn.setOnClickListener{
            findNavController().navigate(R.id.action_robotListScreenFragment_to_reminderScreenFragment)
        }
        return view
    }

}