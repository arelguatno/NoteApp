package com.example.noteapp.aye.room_db.note_table

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteDao: NoteDao
) {

    fun fetchAllData(): Flow<List<Note>> {
        return noteDao.fetchAllData()
    }

    suspend fun insertRecord(note: Note) {
        return noteDao.insertData(note)
    }
}