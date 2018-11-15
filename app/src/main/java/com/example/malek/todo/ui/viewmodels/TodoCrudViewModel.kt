package com.example.malek.todo.ui.viewmodels

import android.content.Context
import com.example.malek.todo.data.Todo
import com.example.malek.todo.data.TodoDataBaseProvider
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class TodoCrudViewModel {
    fun addTodo(context: Context, todo: Todo): Completable {
        return Completable.fromCallable {
            TodoDataBaseProvider.getDb(context)
                .todoDao()
                .addTodo(todo)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getTodos(context: Context): Flowable<List<Todo>> {
        return Flowable.fromCallable {
            TodoDataBaseProvider.getDb(context)
                .todoDao()
                .getTodos()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun deletTodos(context: Context, list: List<Todo>): Single<Int>? {
        return Single.fromCallable {
            TodoDataBaseProvider.getDb(context)
                .todoDao()
                .deletTodo(list)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun updateTodo(context: Context, todo: Todo): Single<Int> {
        return Single.fromCallable {
            TodoDataBaseProvider.getDb(context)
                .todoDao()
                .updateTodo(todo)

        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}