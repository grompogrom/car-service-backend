package com.example.cache

import com.example.features.register.RegisterReceiveRemote
import com.example.features.services.ServiceResponseRemote

data class TokenCache(
    val login: String,
    val token: String
)

object InMemoryCache {
    val userList: MutableList<RegisterReceiveRemote> = mutableListOf()
    val token: MutableList<TokenCache> = mutableListOf()
    val serviceList: List<ServiceResponseRemote> = listOf(
        ServiceResponseRemote(
            1,
            "детейлинг",
            "разбираем на деатли и детейлим"
        )
    )
}