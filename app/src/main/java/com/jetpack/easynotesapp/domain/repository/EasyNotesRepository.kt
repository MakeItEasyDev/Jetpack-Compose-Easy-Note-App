package com.jetpack.easynotesapp.domain.repository

import com.jetpack.easynotesapp.domain.model.EasyNotes
import kotlinx.coroutines.flow.Flow

interface EasyNotesRepository {

    fun getEasyNotes(): Flow<List<EasyNotes>>

    suspend fun getEasyNotesById(id: Int): EasyNotes?

    suspend fun insertEasyNotes(note: EasyNotes)

    suspend fun deleteEasyNotes(note: EasyNotes)
}