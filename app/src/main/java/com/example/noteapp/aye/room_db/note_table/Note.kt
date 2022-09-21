package com.example.noteapp.aye.room_db.note_table

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.noteapp.aye.data.Priority
import java.io.Serializable

@Entity(tableName = "notes_table")
data class Note(
    var title: String? = "",
    var priority: Priority? = Priority.High,
    var description: String? = ""
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}