package com.jetpack.easynotesapp.presentation.addediteasynote

import androidx.compose.ui.focus.FocusState

sealed class AddEditEasyNoteEvent{
    data class EnteredTitle(val value: String): AddEditEasyNoteEvent()
    data class ChangeTitleFocus(val focusState: FocusState): AddEditEasyNoteEvent()
    data class EnteredContent(val value: String): AddEditEasyNoteEvent()
    data class ChangeContentFocus(val focusState: FocusState): AddEditEasyNoteEvent()
    data class ChangeColor(val color: Int): AddEditEasyNoteEvent()
    object SaveNote: AddEditEasyNoteEvent()
}

