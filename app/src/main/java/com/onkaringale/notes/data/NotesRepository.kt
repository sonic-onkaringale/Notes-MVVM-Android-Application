package com.onkaringale.notes.data

import kotlinx.coroutines.flow.Flow

interface NotesRepository
{

    fun insertNote(note: NoteModel)

    fun deleteNote(note: NoteModel)

    fun getNoteById(id: Long): NoteModel?

    fun getAllNotes(): Flow<List<NoteModel>>
}