package com.onkaringale.notes.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class NoteModel(
        @PrimaryKey  val id: Long?,
        var title: String,
        var description: String,
        var timestamp: Long //Uses Instant
                    )
