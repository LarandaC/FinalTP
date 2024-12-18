package com.example.wallpics.constans

sealed interface UiState {
    object Success : UiState
    object Error : UiState
    object Loading : UiState
    object Empty : UiState
}