package com.example.gitsearch.ui.authorScreen

sealed class AuthorFragmentIntent {
    data class GetOwnerById(val ownerId: Int) : AuthorFragmentIntent()
}