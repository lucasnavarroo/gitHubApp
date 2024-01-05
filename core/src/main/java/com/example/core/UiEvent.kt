package com.example.core

sealed class UiEvent {
    data class ShowSnackbar(val message: Int): UiEvent()
}
