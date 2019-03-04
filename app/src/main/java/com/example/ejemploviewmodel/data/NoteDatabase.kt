package com.example.ejemploviewmodel.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Note::class],version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        private var instance: NoteDatabase? = null

        fun getInstance(context: Context): NoteDatabase? {
            if (instance == null) {

                synchronized(NoteDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NoteDatabase::class.java, "note_database"
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()

                }
            }

            return instance
        }

        fun destroyInstance() {
            instance = null

        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance).execute()

            }
        }
    }

    class private PopulateDbAsyncTask(db:NoteDatabase?):AsyncTask<Unit,Unit,Unit>() {
        private val noteDao = db?.NoteDao()

        override fun doInBackground(vararg p0:Unit?){
            noteDao?.insert(Note(title = "title 1",description = "description 1", priority = 1))
            noteDao?.insert(Note(title = "title 2",description = "description 2", priority = 2))
            noteDao?.insert(Note(title = "title 3",description = "description 3", priority = 3))
        }
    }
}