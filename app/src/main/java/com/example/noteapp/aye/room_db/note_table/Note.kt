package com.example.noteapp.aye.room_db.note_table

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "notes_table")
data class Note(
    var name: String = ""
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}