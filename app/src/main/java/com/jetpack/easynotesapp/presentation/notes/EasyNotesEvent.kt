package com.jetpack.easynotesapp.presentation.notes

import com.jetpack.easynotesapp.domain.model.EasyNotes
import com.jetpack.easynotesapp.domain.util.EasyNotesOrder

sealed class EasyNotesEvent {
    data class Order(val noteOrder: EasyNotesOrder): EasyNotesEvent()
    data class DeleteNote(val note: EasyNotes): EasyNotesEvent()
    object RestoreNote: EasyNotesEvent()
    object ToggleOrderSection: EasyNotesEvent()
}
