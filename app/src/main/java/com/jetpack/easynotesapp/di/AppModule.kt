package com.jetpack.easynotesapp.di

import android.app.Application
import androidx.room.Room
import com.jetpack.easynotesapp.data.database.EasyNotesDatabase
import com.jetpack.easynotesapp.data.repository.EasyNotesRepositoryImpl
import com.jetpack.easynotesapp.domain.action.*
import com.jetpack.easynotesapp.domain.repository.EasyNotesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideEasyNoteDatabase(app: Application): EasyNotesDatabase {
        return Room.databaseBuilder(
            app,
            EasyNotesDatabase::class.java,
            EasyNotesDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideEasyNoteRepository(db: EasyNotesDatabase): EasyNotesRepository {
        return EasyNotesRepositoryImpl(db.easyNotesDao)
    }

    @Provides
    @Singleton
    fun provideEasyNoteUseCases(repository: EasyNotesRepository): EasyNoteUseCases {
        return EasyNoteUseCases(
            getEasyNotes = GetEasyNotes(repository),
            deleteEasyNotes = DeleteEasyNotes(repository),
            addEasyNotes = AddEasyNotes(repository),
            getEasyNote = GetEasyNote(repository)
        )
    }
}