package com.example.malek.todo.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Todo(@PrimaryKey(autoGenerate = true) val id: Int, var title: String)