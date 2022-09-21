package com.example.noteapp.aye.room_db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.aye.room_db.note_table.Note
import com.example.noteapp.aye.room_db.note_table.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: NoteRepository
): ViewModel() {

    fun insertRecord(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertRecord(note)
        }
    }
}