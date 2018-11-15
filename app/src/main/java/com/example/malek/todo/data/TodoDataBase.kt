package com.example.malek.todo.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [Todo::class], version = 1)
abstract class TodoDataBase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}

object TodoDataBaseProvider {
    private var db: TodoDataBase? = null
    fun getDb(context: Context): TodoDataBase {
        if (db == null) {
            db = Room.databaseBuilder(
                context,
                TodoDataBase::class.java, "database-name"
            ).build()
        }
        return db as TodoDataBase
    }
}