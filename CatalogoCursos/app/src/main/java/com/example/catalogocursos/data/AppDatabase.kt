package com.example.catalogocursos.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.catalogocursos.model.Curso

@Database(entities = [Curso::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cursoDao(): CursoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cursos_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}