package com.jetpack.easynotesapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jetpack.easynotesapp.domain.model.EasyNotes

@Database(
    entities = [EasyNotes::class],
    version = 1
)
abstract class EasyNotesDatabase: RoomDatabase() {

    abstract val easyNotesDao: EasyNotesDao

    companion object {
        const val DATABASE_NAME = "easyNotes_db"
    }
}