package com.jetpack.easynotesapp.data.database

import androidx.room.*
import com.jetpack.easynotesapp.domain.model.EasyNotes
import kotlinx.coroutines.flow.Flow

@Dao
interface EasyNotesDao {

    @Query("SELECT * FROM easyNotes")
    fun getEasyNotes(): Flow<List<EasyNotes>>

    @Query("SELECT * FROM easyNotes WHERE id = :id")
    suspend fun getEasyNotesById(id: Int): EasyNotes?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEasyNotes(note: EasyNotes)

    @Delete
    suspend fun deleteEasyNotes(note: EasyNotes)
}