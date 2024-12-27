package com.example.easychatgpt.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.easychatgpt.R

class HelplineScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_helpline_screen, container, false)

        val backBtn = view.findViewById<ImageView>(R.id.backImg)
        backBtn.setOnClickListener{
            findNavController().navigate(R.id.action_helplineScreenFragment_to_robotListScreenFragment)
        }

        val answerGroup1 = view.findViewById<RadioGroup>(R.id.answerGroup1)
        val answerGroup2 = view.findViewById<RadioGroup>(R.id.answerGroup2)
        val answerGroup3 = view.findViewById<RadioGroup>(R.id.answerGroup3)
        val answerGroup4 = view.findViewById<RadioGroup>(R.id.answerGroup4)
        val answerGroup5 = view.findViewById<RadioGroup>(R.id.answerGroup5)

        val submitAnswersBtn = view.findViewById<Button>(R.id.submitAnswersBtn)

        submitAnswersBtn.setOnClickListener {
            val isYes1 = view.findViewById<RadioButton>(R.id.answerYes1).isChecked
            val isYes2 = view.findViewById<RadioButton>(R.id.answerYes2).isChecked
            val isYes3 = view.findViewById<RadioButton>(R.id.answerYes3).isChecked
            val isYes4 = view.findViewById<RadioButton>(R.id.answerYes4).isChecked
            val isYes5 = view.findViewById<RadioButton>(R.id.answerYes5).isChecked

            val isNo1 = view.findViewById<RadioButton>(R.id.answerNo1).isChecked
            val isNo2 = view.findViewById<RadioButton>(R.id.answerNo2).isChecked
            val isNo3 = view.findViewById<RadioButton>(R.id.answerNo3).isChecked
            val isNo4 = view.findViewById<RadioButton>(R.id.answerNo4).isChecked
            val isNo5 = view.findViewById<RadioButton>(R.id.answerNo5).isChecked

            // Check if all answers are "Yes"
            if ((isYes1 || isNo1) && (isYes2 || isNo2) && (isYes3 || isNo3) && (isYes4 || isNo4) && (isYes5 || isNo5)) {
                // Show popup window with "Go to Helpline?" Yes/No
                showHelplinePopup()
            } else {
                // Show popup window with a message that not all answers are "Yes"
                showSelectionReminderPopup()
            }
        }

        return view
    }

    // Function to show the "Go to Helpline" popup
    private fun showHelplinePopup() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Go to Helpline")
        builder.setMessage("Would you like to go to the helpline page? Or Would you like to use our chatbot")

        builder.setPositiveButton("Help-page") { dialog, _ ->
            // Navigate to Helpline Screen
            findNavController().navigate(R.id.action_helplineScreenFragment_to_helppageScreenFragment)
            dialog.dismiss()
        }

        // If No is selected
        builder.setNegativeButton("Chatbot") { dialog, _ ->
            findNavController().navigate(R.id.action_helplineScreenFragment_to_chatScreenFragment)
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show() // Show the dialog
    }


    // Function to show reminder popup if no choice is made
    private fun showSelectionReminderPopup() {
        val reminderDialog = AlertDialog.Builder(requireContext())
            .setTitle("Reminder")
            .setMessage("Please select Yes or No to continue.")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        reminderDialog.show()
    }
}
