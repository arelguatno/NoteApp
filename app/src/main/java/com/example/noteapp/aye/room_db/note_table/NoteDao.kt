package com.example.noteapp.aye.room_db.note_table

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes_table ORDER BY id")
    fun fetchAllData(): Flow<List<Note>>
}