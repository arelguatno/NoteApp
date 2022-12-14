package com.example.noteapp.aye

import android.text.TextUtils
import androidx.lifecycle.*
import com.example.noteapp.aye.data.Priority
import com.example.noteapp.aye.room_db.note_table.Note
import com.example.noteapp.aye.room_db.note_table.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkIfDatabaseEmpty(note: List<Note>) {
        emptyDatabase.value = note.isEmpty()
    }

    fun fetchAllData(): LiveData<List<Note>> {
        return repository.fetchAllData().asLiveData()
    }

    fun sortByLowPriority(): LiveData<List<Note>> {
        return repository.sortByLowPriority().asLiveData()
    }

    fun sortByHighPriority(): LiveData<List<Note>> {
        return repository.sortByHighPriority().asLiveData()
    }


    fun searchDatabase(q: String): LiveData<List<Note>> {
        return repository.searchDatabase(q).asLiveData()
    }

    fun insertRecord(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertRecord(note)
        }
    }

    fun updateData(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(note)
        }
    }

    fun deleteData(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteData(note)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    fun verifyDataUser(title: String, description: String): Boolean {
        return if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
            false
        } else !(title.isEmpty() || description.isEmpty())
    }

    fun parsePriority(priority: String): Priority {
        return when (priority) {
            "High Priority" -> {
                Priority.High
            }
            "Medium Priority" -> {
                Priority.Medium
            }
            "Low Priority" -> {
                Priority.Low
            }
            else -> Priority.Low
        }
    }

    fun parsePriorityToInt(priority: Priority): Int {
        return when (priority) {
            Priority.High -> 0
            Priority.Medium -> 1
            Priority.Low -> 2
        }
    }
}