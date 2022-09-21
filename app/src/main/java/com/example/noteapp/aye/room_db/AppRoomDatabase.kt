package com.example.noteapp.aye.room_db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.noteapp.aye.room_db.note_table.Converter
import com.example.noteapp.aye.room_db.note_table.Note
import com.example.noteapp.aye.room_db.note_table.NoteDao

@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class AppRoomDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao
}