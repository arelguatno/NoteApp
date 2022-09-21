package com.example.noteapp.aye.room_db.note_table

import androidx.room.TypeConverter
import com.example.noteapp.aye.data.Priority


class Converter {
    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }
}