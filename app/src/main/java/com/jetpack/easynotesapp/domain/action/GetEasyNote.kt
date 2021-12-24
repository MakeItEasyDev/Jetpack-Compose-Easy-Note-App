package com.jetpack.easynotesapp.domain.action

import com.jetpack.easynotesapp.domain.model.EasyNotes
import com.jetpack.easynotesapp.domain.repository.EasyNotesRepository

class GetEasyNote(
    private val repository: EasyNotesRepository
) {
    suspend operator fun invoke(id: Int): EasyNotes? {
        return repository.getEasyNotesById(id)
    }
}