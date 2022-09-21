package com.example.noteapp.aye.room_db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.noteapp.aye.room_db.note_table.Note
import com.example.noteapp.aye.room_db.note_table.NoteDao

@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)
abstract class AppRoomDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao
}