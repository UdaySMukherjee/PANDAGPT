package com.example.easychatgpt.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.easychatgpt.R
import com.example.easychatgpt.adapters.ChatAdapter
import com.example.easychatgpt.models.Chat
import com.example.easychatgpt.utils.copyToClipBoard
import com.example.easychatgpt.utils.hideKeyBoard
import com.example.easychatgpt.utils.longToastShow
import com.example.easychatgpt.utils.shareMsg
import com.example.easychatgpt.viewModels.ChatViewModel
import java.util.Date


class ChatScreenFragment : Fragment() {

    private val chatList = arrayListOf(
        Chat(
            "1",
            "Hi, how can I integrate Retrofit in Android development?",
            "sender",
            Date()
        ),
        Chat(
            "2",
            "You can integrate Retrofit by adding it as a dependency in your app's build.gradle file.",
            "receiver",
            Date()
        ),
        Chat(
            "3",
            "What is the purpose of ViewModel in MVVM architecture?",
            "sender",
            Date()
        ),
        Chat(
            "4",
            "ViewModel is used to store and manage UI-related data, separating it from the UI components.",
            "receiver",
            Date()
        ),
        Chat(
            "5",
            "What is the purpose of ViewModel in MVVM architecture?",
            "sender",
            Date()
        ),
        Chat(
            "6",
            "ViewModel is used to store and manage UI-related data, separating it from the UI components.",
            "receiver",
            Date()
        ),
        Chat(
            "7",
            "How do I implement a RecyclerView in Android?",
            "sender",
            Date()
        ),
        Chat(
            "8",
            "You can create a RecyclerView by defining a layout, adapter, and connecting it to your data source.",
            "receiver",
            Date()
        ),
        Chat(
            "9",
            "Can you suggest a good Android IDE?",
            "sender",
            Date()
        ),
        Chat(
            "10",
            "Android Studio is the official IDE for Android app development and is highly recommended.",
            "receiver",
            Date()
        ),
        Chat(
            "11",
            "How can I implement a swipe-to-refresh feature in my Android app?",
            "sender",
            Date()
        ),
        Chat(
            "12",
            "You can implement swipe-to-refresh by using the SwipeRefreshLayout widget in your layout XML and handling refresh events in your code.",
            "receiver",
            Date()
        )
    )

    private var pendingMessage: String? = null

    private val chatViewModel : ChatViewModel by lazy {
        ViewModelProvider(this)[ChatViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_chat_screen, container, false)

        val backBtn = view.findViewById<ImageView>(R.id.backImg)
        backBtn.setOnClickListener{
            findNavController().navigate(R.id.action_chatScreenFragment_to_robotListScreenFragment)
        }

        val chatRV = view.findViewById<RecyclerView>(R.id.ChatRV)
        val chatAdapter = ChatAdapter(){ message, textView ->
            val popup = PopupMenu(context,textView)
            try {
                val fields = popup.javaClass.declaredFields
                for (field in fields) {
                    if ("mPopup" == field.name) {
                        field.isAccessible = true
                        val menuPopupHelper = field.get(popup)
                        val classPopupHelper = Class.forName(menuPopupHelper.javaClass.name)
                        val setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon",
                            Boolean::class.javaPrimitiveType
                        )
                        setForceIcons.invoke(menuPopupHelper, true)
                        break
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            popup.menuInflater.inflate(R.menu.option_menu, popup.menu)

            popup.setOnMenuItemClickListener { item ->
                when(item.itemId){
                    R.id.copyMenu ->{
                        view.context.copyToClipBoard(message)
                        return@setOnMenuItemClickListener true
                    }
                    R.id.selectTextMenu ->{
                        val action =
                            ChatScreenFragmentDirections.actionChatScreenFragmentToSelectTextScreenFragment(
                                message
                            )
                        findNavController().navigate(action)
                        return@setOnMenuItemClickListener true
                    }
                    R.id.shareTextMenu ->{
                        view.context.shareMsg(message)
                        return@setOnMenuItemClickListener true
                    }
                    else ->{
                        return@setOnMenuItemClickListener true
                    }
                }
            }
        }
        chatRV.adapter = chatAdapter

        val sendImageBtn = view.findViewById<ImageButton>(R.id.sendImageBtn)
        val edMessage = view.findViewById<EditText>(R.id.edMessage)
        var counter = -1
//        sendImageBtn.setOnClickListener{
//            view.context.hideKeyBoard(it)
//            if (edMessage.text.toString().trim().isNotEmpty()) {
//                counter += 1
//                if (counter >= chatList.size) {
//                    return@setOnClickListener
//                }
//                chatViewModel.insertChat(chatList[counter])
//
//            }else{
//                view.context.longToastShow("Message is Required")
//            }
//        }

        sendImageBtn.setOnClickListener {
            view.context.hideKeyBoard(it)

            val userMessage = edMessage.text.toString().trim()
            if (userMessage.isNotEmpty()) {
                // Clear the input field
                edMessage.setText("")

                // Send the message through the ViewModel
                chatViewModel.sendChatMessage(userMessage, "sender")
            } else {
                view.context.longToastShow("Message is required")
            }
        }

//        chatViewModel.chatList.observe(viewLifecycleOwner){
//            chatAdapter.submitList(it)
//            chatRV.smoothScrollToPosition(it.size)
//        }



        chatViewModel.chatList.observe(viewLifecycleOwner) { chatMessages ->
            chatAdapter.submitList(chatMessages)
            chatRV.smoothScrollToPosition(chatMessages.size)  // Scroll to the latest message
        }
        chatViewModel.showNegativeWordPopup.observe(viewLifecycleOwner) { shouldShow ->
            if (shouldShow) {
                // Store the pending message that triggered the dialog
                pendingMessage = chatViewModel.latestMessage.value
                showNegativeWordDialog()
            }
        }

        return view
    }

    // Function to show popup dialog with two options
    private fun showNegativeWordDialog() {
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        builder.setTitle("Warning")
        builder.setMessage("It seems like you're expressing negative thoughts. Would you like to talk to someone?")

        builder.setPositiveButton("Get Help") { dialog, _ ->
            // Navigate to Helpline Screen
            findNavController().navigate(R.id.action_chatScreenFragment_to_helplineScreenFragment)
            dialog.dismiss()
        }

        // If No is selected
        builder.setNegativeButton("Cancel") { dialog, _ ->
            // Continue chat with the pending message
            pendingMessage?.let { chatViewModel.continueWithPendingMessage(it, "sender") }
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

}