package com.onkaringale.notes.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: NoteModel)

    @Delete
    fun deleteNote(note: NoteModel)

    @Query("SELECT * FROM NoteModel WHERE id = :id")
    fun getNoteById(id: Long): NoteModel?

    @Query("SELECT * FROM NoteModel")
    fun getAllNotes(): Flow<List<NoteModel>>
}