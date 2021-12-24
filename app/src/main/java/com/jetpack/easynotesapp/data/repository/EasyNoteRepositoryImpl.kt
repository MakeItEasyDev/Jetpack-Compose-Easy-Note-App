package com.jetpack.easynotesapp.data.repository

import com.jetpack.easynotesapp.data.database.EasyNotesDao
import com.jetpack.easynotesapp.domain.model.EasyNotes
import com.jetpack.easynotesapp.domain.repository.EasyNotesRepository
import kotlinx.coroutines.flow.Flow

class EasyNotesRepositoryImpl(
    private val dao: EasyNotesDao
) : EasyNotesRepository {

    override fun getEasyNotes(): Flow<List<EasyNotes>> {
        return dao.getEasyNotes()
    }

    override suspend fun getEasyNotesById(id: Int): EasyNotes? {
        return dao.getEasyNotesById(id)
    }

    override suspend fun insertEasyNotes(note: EasyNotes) {
        dao.insertEasyNotes(note)
    }

    override suspend fun deleteEasyNotes(note: EasyNotes) {
        dao.deleteEasyNotes(note)
    }
}