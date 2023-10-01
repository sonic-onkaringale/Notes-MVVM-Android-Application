package com.onkaringale.notes.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase


@Database(
        entities = [NoteModel::class],
        version = 1
         )
abstract class NotesDatabase:RoomDatabase()
{
    abstract val dao:NotesDao


}