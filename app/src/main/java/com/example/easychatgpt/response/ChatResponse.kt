package com.example.easychatgpt.response

data class ChatResponse(
    val id: String,
    val message: String,
    val sender: String,
    val date: String? = null
)

