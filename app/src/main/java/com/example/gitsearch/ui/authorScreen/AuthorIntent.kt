package com.example.gitsearch.ui.authorScreen

sealed class AuthorIntent {
    data class GetOwnerById(val ownerId: Int) : AuthorIntent()
}