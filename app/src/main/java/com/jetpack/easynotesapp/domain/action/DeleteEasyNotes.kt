package com.jetpack.easynotesapp.domain.action

import com.jetpack.easynotesapp.domain.model.EasyNotes
import com.jetpack.easynotesapp.domain.repository.EasyNotesRepository

class DeleteEasyNotes(
    private val repository: EasyNotesRepository
) {
    suspend operator fun invoke(easyNotes: EasyNotes) {
        repository.deleteEasyNotes(easyNotes)
    }
}