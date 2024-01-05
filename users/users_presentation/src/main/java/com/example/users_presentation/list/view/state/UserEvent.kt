package com.example.users_presentation.list.view.state

sealed class UserEvent {
    object OnLoad : UserEvent()
    data class OnQueryChange(val query: String) : UserEvent()
    object OnSearch : UserEvent()
    data class OnSearchFocusChange(val isFocused: Boolean): UserEvent()
}