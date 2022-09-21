package com.example.noteapp.aye.module

import android.content.Context
import androidx.room.Room
import com.example.noteapp.aye.room_db.AppRoomDatabase
import com.example.noteapp.aye.room_db.note_table.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class MyModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        AppRoomDatabase::class.java,
        "com.example.noteapp.aye_db"
    ).build()


    @Provides
    fun provideNoteDao(appRoomDatabase: AppRoomDatabase): NoteDao {
        return appRoomDatabase.noteDao()
    }
}