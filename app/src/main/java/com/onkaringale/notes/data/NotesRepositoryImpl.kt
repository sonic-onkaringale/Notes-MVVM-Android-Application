package com.onkaringale.notes.data

import kotlinx.coroutines.flow.Flow

class NotesRepositoryImpl
(private val dao: NotesDao)
    : NotesRepository
{
    override fun insertNote(note: NoteModel)
    {
        dao.insertNote(note)
    }

    override fun deleteNote(note: NoteModel)
    {
        dao.deleteNote(note)
    }

    override fun getNoteById(id: Long): NoteModel?
    {
        return dao.getNoteById(id)
    }

    override fun getAllNotes(): Flow<List<NoteModel>>
    {
        return dao.getAllNotes()
    }
}