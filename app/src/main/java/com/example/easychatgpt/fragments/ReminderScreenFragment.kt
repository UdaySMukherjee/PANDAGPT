package com.example.easychatgpt.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.easychatgpt.R
import com.example.easychatgpt.viewModels.ChatViewModel
import java.util.Calendar

class ReminderScreenFragment : Fragment() {

    private lateinit var calendarView: CalendarView
    private lateinit var eventName: EditText
    private lateinit var eventTime: EditText
    private lateinit var setReminderButton: Button
    private lateinit var chatViewModel: ChatViewModel

    private var selectedDate: Long = 0L // Store selected date from CalendarView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_reminder_screen, container, false)

        val backBtn = view.findViewById<ImageView>(R.id.backImg)
        backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_reminderScreenFragment_to_robotListScreenFragment)
        }

        // Initialize views
        calendarView = view.findViewById(R.id.calendarView)
        eventName = view.findViewById(R.id.eventName)
        eventTime = view.findViewById(R.id.eventTime)
        setReminderButton = view.findViewById(R.id.setReminderButton)

        // Get ViewModel instance
        chatViewModel = ViewModelProvider(requireActivity()).get(ChatViewModel::class.java)

        // Handle date selection from CalendarView
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            selectedDate = calendar.timeInMillis
        }

        // Listen for the "bye" event with actual time
        chatViewModel.byeEventTriggered.observe(viewLifecycleOwner) { (timestamp, _) ->
            markEventWithGoodbyeMessage(timestamp)
        }

        // Handle setting the reminder
        setReminderButton.setOnClickListener {
            val name = eventName.text.toString()
            val time = eventTime.text.toString()

            if (name.isNotEmpty() && time.isNotEmpty()) {
                addEventToCalendar(name, time, selectedDate)
            } else {
                // Show a message to the user to fill out all fields
            }
        }

        return view
    }

    // Function to mark the current date and exact time with a "Goodbye" event
    private fun markEventWithGoodbyeMessage(timestamp: Long) {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp

        // Automatically fill the event name
        eventName.setText("Checkup Done")

        // Format the time from the timestamp to a user-friendly format, e.g., "12:34 PM"
        val timeFormat = android.text.format.DateFormat.getTimeFormat(context)
        val formattedTime = timeFormat.format(calendar.time)
        eventTime.setText(formattedTime)

        // Use timestamp as the event time
        addEventToCalendar("Checkup Done", formattedTime, timestamp)
    }

    // Function to add the event to the calendar
    private fun addEventToCalendar(eventTitle: String, eventTime: String, eventDate: Long) {
        // Add calendar event logic here using `eventDate` and `eventTime`
        // Example: Set event title and time in the user's calendar
    }
}

