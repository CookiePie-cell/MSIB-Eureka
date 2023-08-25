package com.salugan.todolist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.salugan.todolist.data.BookRepository
import com.salugan.todolist.data.remote.FirebaseDataSource
import com.salugan.todolist.di.Injection
import com.salugan.todolist.ui.Fragment.AddBookFragmentViewModel
import com.salugan.todolist.ui.activity.main.MainViewModel

class ViewModelFactory private constructor(
    private val bookRepository: BookRepository
    ) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(bookRepository) as T
        } else if (modelClass.isAssignableFrom(AddBookFragmentViewModel::class.java)) {
            return AddBookFragmentViewModel(bookRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        fun getInstance(): ViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory(Injection.provideRepository())
            }.also { INSTANCE = it }
    }
}