package com.jetpack.easynotesapp.domain.action

import com.jetpack.easynotesapp.domain.model.EasyNotes
import com.jetpack.easynotesapp.domain.model.InvalidNoteException
import com.jetpack.easynotesapp.domain.repository.EasyNotesRepository

class AddEasyNotes(
    private val repository: EasyNotesRepository
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(easyNotes: EasyNotes) {
        if(easyNotes.title.isEmpty()) {
            throw InvalidNoteException("Please enter Title!")
        }
        if(easyNotes.content.isEmpty()) {
            throw InvalidNoteException("Please enter Content!")
        }
        repository.insertEasyNotes(easyNotes)
    }
}