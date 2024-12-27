package com.example.easychatgpt.models

import java.util.Date

//data class Chat(
//    val chatId: String,
//    val message: String,
//    val messageType: String,
//    val date: java.util.Date,
//)
data class Chat(
    val chatId: String,
    val message: String,  // Ensure this is a non-nullable String
    val messageType: String,
    val date: Date
)
