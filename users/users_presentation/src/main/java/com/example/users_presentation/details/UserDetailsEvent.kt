package com.example.users_presentation.details

sealed class UserDetailsEvent {
    data class OnLoad(val login: String) : UserDetailsEvent()
}