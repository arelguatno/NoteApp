package com.example.noteapp.aye

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.noteapp.aye.databinding.ActivityMainBinding
import com.example.noteapp.aye.room_db.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item = com.example.noteapp.aye.room_db.note_table.Note(name = "aye")
        viewModel.insertRecord(item)
    }
}

