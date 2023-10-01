package com.onkaringale.notes.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onkaringale.notes.data.NoteModel
import com.onkaringale.notes.data.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelMainActivity @Inject constructor(val repository: NotesRepository) : ViewModel()
{


    val notes: MutableLiveData<List<NoteModel>> = MutableLiveData()

    fun getAllNotes(): LiveData<List<NoteModel>>
    {
        return notes
    }

    var isRunnableRan = false

    fun initialize(runnable: Runnable)
    {
        if (notes.isInitialized) return
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllNotes().collectLatest {
                    Log.d("Debug", "ran runnable")
                    if (!isRunnableRan) runnable.run()
                    isRunnableRan = true
                    notes.postValue(it)
                }
        }
    }


}
