package com.example.easychatgpt.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easychatgpt.models.Chat
import com.example.easychatgpt.network.RetrofitClient
import com.example.easychatgpt.response.ChatRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

//class ChatViewModel (application: Application) : AndroidViewModel(application){
//
//
//    var chatList = MutableLiveData<List<Chat>>(arrayListOf())
//        private set
//
//    fun insertChat(chat: Chat){
//        val modifiedChatList = ArrayList<Chat>().apply {
//            addAll(chatList.value!!)
//        }
//        modifiedChatList.add(chat)
//        chatList.postValue(modifiedChatList)
//    }
//}


//class ChatViewModel(application: Application) : AndroidViewModel(application) {
//
//    var chatList = MutableLiveData<List<Chat>>(arrayListOf())
//        private set
//
//    // Add a LiveData for triggering popup in the UI
//    val showNegativeWordPopup = MutableLiveData<Boolean>()
//
//    private val negativeWords = listOf("die", "suicide")
//
//    fun insertChat(chat: Chat) {
//        val modifiedChatList = ArrayList<Chat>().apply {
//            addAll(chatList.value!!)
//        }
//        modifiedChatList.add(chat)
//        chatList.postValue(modifiedChatList)
//    }
//
//    fun sendChatMessage(message: String, sender: String) {
//        if (message.isBlank()) {
//            return
//        }
//
//        // Check for negative words
//        if (containsNegativeWords(message)) {
//            showNegativeWordPopup.postValue(true)  // Notify fragment to show popup
//            return
//        }
//
//        val newChatId = UUID.randomUUID().toString()
//        val newChat = Chat(newChatId, message, sender, Date())
//
//        insertChat(newChat)
//
//        // Proceed to send message to the server (same logic as before)
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val chatRequest = ChatRequest(message = message, sender = sender)
//                val chatResponse = RetrofitClient.chatService.getChatResponse(chatRequest)
//
//                val llmResponseChat = Chat(
//                    chatId = UUID.randomUUID().toString(),
//                    message = chatResponse.message ?: "No response",
//                    messageType = "receiver",
//                    date = Date()
//                )
//
//                insertChat(llmResponseChat)
//
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }
//
//    // Helper function to detect negative words
//    private fun containsNegativeWords(message: String): Boolean {
//        return negativeWords.any { word ->
//            message.contains(word, ignoreCase = true)
//        }
//    }
//}

class ChatViewModel(application: Application) : AndroidViewModel(application) {

    var chatList = MutableLiveData<List<Chat>>(arrayListOf())
        private set

    // New LiveData to trigger a reminder with time when the user says "bye"
    val byeEventTriggered = MutableLiveData<Pair<Long, String>>()  // Pair of timestamp and message


    // Track if a popup should be shown
    val showNegativeWordPopup = MutableLiveData<Boolean>()
    val latestMessage = MutableLiveData<String?>()  // To store the latest message
    private var hasShownWarning = false  // New flag to track if the warning was already shown

    // Insert chat message into the chat list
    fun insertChat(chat: Chat) {
        val modifiedChatList = ArrayList<Chat>().apply {
            addAll(chatList.value!!)
        }
        modifiedChatList.add(chat)
        chatList.postValue(modifiedChatList)
    }

    // Continue chat when user presses "Cancel" in dialog
    fun continueWithPendingMessage(message: String, sender: String) {
        // Bypass negative word check after user presses "Cancel"
        hasShownWarning = true
        sendChatMessage(message, sender)
    }

    // Send chat message after checking for negative words
    fun sendChatMessage(message: String, sender: String) {
        if (message.isBlank()) {
            return  // Handle empty message case
        }

        // Check if the message contains negative words and the warning hasn't been shown yet
        if (containsNegativeWords(message) && !hasShownWarning) {
            latestMessage.postValue(message)  // Store the latest message
            showNegativeWordPopup.postValue(true)  // Trigger popup
            return
        }

        // Check if the user said "bye"
        if (message.equals("bye", ignoreCase = true)) {
            // Capture the current time in milliseconds
            val currentTime = System.currentTimeMillis()
            byeEventTriggered.postValue(Pair(currentTime, message))  // Trigger calendar marking with the time
        }

        // Reset the flag for the next message after processing
        hasShownWarning = false

        // Normal flow: send message
        val newChatId = UUID.randomUUID().toString()
        val newChat = Chat(newChatId, message, sender, Date())

        insertChat(newChat)

        // Simulate sending message to server and receiving a response
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val chatRequest = ChatRequest(message = message, sender = sender)
                val chatResponse = RetrofitClient.chatService.getChatResponse(chatRequest)

                val llmResponseChat = Chat(
                    chatId = UUID.randomUUID().toString(),
                    message = chatResponse.message ?: "No response",
                    messageType = "receiver",
                    date = Date()
                )

                insertChat(llmResponseChat)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Method to detect negative words
    private fun containsNegativeWords(message: String): Boolean {
        val negativeWords = listOf(
        "die",
        "suicide",
        "kill",
        "end it all",
        "self-harm",
        "suffering",
        "can't go on",
        "cut",
        "give up",
        "no way out",
        "drowning")
        return negativeWords.any { message.contains(it, ignoreCase = true) }
    }


}
