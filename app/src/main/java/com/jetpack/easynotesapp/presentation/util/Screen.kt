package com.jetpack.easynotesapp.presentation.util

sealed class Screen(val route: String) {
    object EasyNotesScreen: Screen("notes_screen")
    object AddEditEasyNoteScreen: Screen("add_edit_note_screen")
}
