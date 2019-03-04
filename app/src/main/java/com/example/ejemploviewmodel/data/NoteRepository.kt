package com.example.ejemploviewmodel.data

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.room.Delete

class NoteRepository(application: Application){
    private var noteDao:NoteDao

    private var allNote:LiveData<List<Note>>

    //Metodo pra inicializarla
    init {
        val database:NoteDatabase.getInstance(
        application.applicationContext
        )!!

        noteDao = database.noteDao()
        allNote = noteDao.getAllNotes()
    }
    fun insert (note:Note){
        InsertNoteAsyncTask(noteDao).execute(note)
    }
    fun update (note: Note){
        UpdateNoteAsyncTask(noteDao).execute(note)
    }
    fun delete(note: Note){
        DeleteNoteAsyncTask(noteDao).execute(note)
    }
    fun deleteAllNotes(){
        DeleteAllNotesAsyncTask(noteDao).execute()

    }
    fun getAllNotes():LiveData<List<Note>>{
        return allNote
    }

    companion object {
        private class InsertNoteAsyncTask(noteDao:NoteDao): AsyncTask<Note,Unit,Unit>(){
            val noteDao = noteDao
            override fun doInBackground(vararg p0: Note?) {
                noteDao.insert(p0[0]!!)
            }
        }

        private class UpdateNoteAsyncTask(noteDao:NoteDao): AsyncTask<Note,Unit,Unit>(){
            val noteDao = noteDao
            override fun doInBackground(vararg p0: Note?) {
                noteDao.update(p0[0]!!)
            }
        }

        private class DeleteNoteAsyncTask(noteDao:NoteDao): AsyncTask<Note,Unit,Unit>(){
            val noteDao = noteDao
            override fun doInBackground(vararg p0: Note?) {
                noteDao.delete(p0[0]!!)
            }
        }

        private class DeleteAllNotesAsyncTask(noteDao:NoteDao): AsyncTask<Note,Unit,Unit>(){
            val noteDao = noteDao
            override fun doInBackground(vararg params: Note?) {
                noteDao.deleteAllNotes()
            }

        }

    }
}