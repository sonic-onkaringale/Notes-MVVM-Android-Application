package com.onkaringale.notes.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onkaringale.notes.data.NoteModel
import com.onkaringale.notes.data.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class ViewModelCreateNoteActivity @Inject constructor(val repository: NotesRepository) : ViewModel()
{
    var isInitialized = MutableLiveData(false)
        private set

    lateinit var note: NoteModel
    fun initialize(id: Long? = null, runnable: Runnable? = null)
    {
        if (isInitialized.value!!) return
        note = NoteModel(null, "", "", Instant.now().toEpochMilli())
        if (id != null)
        {
            viewModelScope.launch(Dispatchers.IO) {
                val temp = repository.getNoteById(id)
                if (temp != null) note = temp
                withContext(Dispatchers.Main) {
                    isInitialized.value = true
                    runnable?.run()
                }
            }
        }
        else
        {
            isInitialized.value = true
            runnable?.run()
        }

    }

    fun save(runnable: Runnable)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertNote(note)
            withContext(Dispatchers.Main) {
                runnable.run()
            }
        }
    }

    fun delete(runnable: Runnable)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(note)
            withContext(Dispatchers.Main) {
                runnable.run()
            }
        }
    }
}