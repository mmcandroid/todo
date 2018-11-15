package com.example.malek.todo.ui.activitys

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.widget.Toast
import com.example.malek.todo.R
import com.example.malek.todo.data.Todo
import com.example.malek.todo.ui.adapters.OnTodoClickListener
import com.example.malek.todo.ui.adapters.TodoAdapter
import com.example.malek.todo.ui.viewmodels.TodoCrudViewModel
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_todo_dialgo.view.*
import timber.log.Timber

class MainActivity : AppCompatActivity(), OnTodoClickListener {
    override fun onTodoClick(todo: Todo) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.add_todo_dialgo, null)
        dialogView.editText.setText(todo.title)
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Edit Todo")
            .setView(dialogView)
            .setPositiveButton("ok") { _, _ ->
                dialogView?.editText?.text.toString().let { newTitle ->
                    todo.title = newTitle
                    disposables.add(
                        crudViewModel.updateTodo(applicationContext, todo)
                            .subscribe({
                                Timber.e("update %s", it)
                                getTodo()
                            }, {
                                Timber.e(it.toString())
                            })
                    )
                }
            }
            .setNegativeButton("cancel") { _, _ ->

            }
        builder.show()
    }

    private val disposables = CompositeDisposable()
    val crudViewModel = TodoCrudViewModel()
    val adapter = TodoAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        add.setOnClickListener { _ ->
            addTodo()
        }
        todoRecyclerView.layoutManager = LinearLayoutManager(this)
        todoRecyclerView.adapter = adapter
        getTodo()
    }

    @SuppressLint("CheckResult")
    fun getTodo() {
        disposables.add(crudViewModel.getTodos(applicationContext)
            .flatMap {
                Flowable.fromIterable(it)
            }
            .doOnSubscribe {
                adapter.todos.clear()
            }
            .subscribe({ todo: Todo? ->
                todo?.let {
                    adapter.todos.add(it)
                    adapter.notifyDataSetChanged()
                }
            }, {
                Timber.e(it.toString())
            })
        )
    }

    private fun addTodo() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.add_todo_dialgo, null)
        val builder = AlertDialog.Builder(this)
        builder.setTitle("New Todo")
            .setView(dialogView)
            .setPositiveButton("ok") { _, _ ->

            }
            .setNegativeButton("cancel") { _, _ ->

            }
        val dialog = builder.create()
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener { _ ->
            if (dialogView.editText.text.isBlank()) {
                dialogView.editText.error = "Is Blank"
            } else {
                disposables.add(
                    crudViewModel.addTodo(applicationContext, Todo(0, dialogView.editText.text.toString()))
                        .subscribe({
                            Toast.makeText(this, "Todo added", Toast.LENGTH_SHORT).show()
                            dialog.dismiss()
                            getTodo()
                        }, {
                            Toast.makeText(this, "add Todo fail", Toast.LENGTH_SHORT).show()
                            dialog.dismiss()
                        })
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}
