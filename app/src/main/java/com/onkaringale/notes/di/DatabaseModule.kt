package com.onkaringale.notes.di

import android.content.Context
import androidx.room.Room
import com.onkaringale.notes.data.NotesDatabase
import com.onkaringale.notes.data.NotesRepository
import com.onkaringale.notes.data.NotesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule
{
    @Provides
    @Singleton
    fun provideNotesDatabase(@ApplicationContext context:Context): NotesDatabase
    {
        return Room.databaseBuilder(
                context,
                NotesDatabase::class.java, "notes_db"
                                   ).build()
    }

    @Provides
    @Singleton
    fun provideNotesRepository(db:NotesDatabase):NotesRepository
    {
        return NotesRepositoryImpl(db.dao)
    }
}