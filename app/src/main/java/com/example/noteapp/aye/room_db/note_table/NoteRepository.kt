package com.example.noteapp.aye.room_db.note_table

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteDao: NoteDao
) {

    fun fetchAllData(): Flow<List<Note>> {
        return noteDao.fetchAllData()
    }

    fun sortByHighPriority(): Flow<List<Note>> {
        return noteDao.sortByHighPriority()
    }

    fun sortByLowPriority(): Flow<List<Note>> {
        return noteDao.sortByLowPriority()
    }

    suspend fun insertRecord(note: Note) {
        return noteDao.insertData(note)
    }

    suspend fun updateData(note: Note) {
        return noteDao.updateData(note)
    }

    suspend fun deleteData(note: Note) {
        return noteDao.deleteData(note)
    }

    suspend fun deleteAll() {
        return noteDao.deleteAll()
    }

    fun searchDatabase(q: String): Flow<List<Note>> {
        return noteDao.searchDatabase(q)
    }
}