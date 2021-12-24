package com.jetpack.easynotesapp.domain.action

data class EasyNoteUseCases(
    val getEasyNotes: GetEasyNotes,
    val deleteEasyNotes: DeleteEasyNotes,
    val addEasyNotes: AddEasyNotes,
    val getEasyNote: GetEasyNote
)
