package com.salugan.todolist.ui.activity.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.salugan.todolist.data.BookRepository
import com.salugan.todolist.data.Result
import com.salugan.todolist.model.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    bookRepository: BookRepository
) : ViewModel() {

    val listBook: LiveData<Result<List<Book>>> = bookRepository.getListBook()

}