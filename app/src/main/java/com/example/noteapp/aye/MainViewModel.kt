package com.example.noteapp.aye

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.noteapp.aye.room_db.note_table.Note
import com.example.noteapp.aye.room_db.note_table.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: NoteRepository
): ViewModel() {

    fun fetchAllData(): LiveData<List<Note>> {
        return repository.fetchAllData().asLiveData()
    }

    fun insertRecord(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertRecord(note)
        }
    }
}