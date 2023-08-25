package com.salugan.todolist.ui.Fragment

import androidx.lifecycle.ViewModel
import com.salugan.todolist.data.BookRepository
import com.salugan.todolist.model.Book

class AddBookFragmentViewModel(private val bookRepository: BookRepository) : ViewModel() {

    fun addBook(book: Book) {
        bookRepository.addBook(book)
    }
}