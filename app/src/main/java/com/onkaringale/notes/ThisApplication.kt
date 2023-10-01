package com.onkaringale.notes

import android.app.Application
import androidx.room.Room
import com.google.android.material.color.DynamicColors
import com.onkaringale.notes.data.NotesDatabase
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class ThisApplication :Application()
{
    //    val db = Room.databaseBuilder(
//            applicationContext,
//    NotesDatabase::class.java, "notes_db"
//    ).build()
    override fun onCreate()
    {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this);
    }
}
