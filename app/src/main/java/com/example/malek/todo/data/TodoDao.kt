package com.example.malek.todo.data

import android.arch.persistence.room.*

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    fun getTodos(): List<Todo>

    @Insert
    fun addTodo(vararg todos: Todo): List<Long>

    @Update
    fun updateTodo(todo: Todo): Int

    @Delete
    fun deletTodo(todos: List<Todo>): Int
}