package com.jetpack.easynotesapp.presentation.addediteasynote

data class EasyNoteTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)
