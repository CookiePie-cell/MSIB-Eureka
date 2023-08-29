package com.salugan.todolist.ui.Fragment

import androidx.lifecycle.ViewModel
import com.salugan.todolist.data.BookRepository
import com.salugan.todolist.model.Book

class BookFragmentViewModel(private val bookRepository: BookRepository) : ViewModel() {

    fun addBook(book: Book) {
        bookRepository.addBook(book)
    }

    fun editBook(book: Book) {
        bookRepository.editBook(book)
    }
}