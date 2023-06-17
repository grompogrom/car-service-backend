package com.example.utils

import com.example.database.tokens.Tokens

object TokenCheck {
    fun isTokenValid(token: String): Boolean = Tokens.fetchToken(token) != null

    fun isTokenAdmin(token: String): Boolean = token == "car-app-admin-token-93749"
}