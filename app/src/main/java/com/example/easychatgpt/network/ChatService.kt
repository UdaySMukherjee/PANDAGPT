package com.example.easychatgpt.network

import com.example.easychatgpt.response.ChatRequest
import com.example.easychatgpt.response.ChatResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ChatService {
    @Headers("Content-Type: application/json")
    @POST("/chat")
    suspend fun getChatResponse(@Body request: ChatRequest): ChatResponse
}


