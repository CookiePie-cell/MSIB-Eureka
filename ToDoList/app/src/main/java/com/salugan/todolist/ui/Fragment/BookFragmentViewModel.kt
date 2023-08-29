package com.salugan.todolist.ui.Fragment

import androidx.lifecycle.ViewModel
import com.salugan.todolist.data.BookRepository
import com.salugan.todolist.model.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookFragmentViewModel @Inject constructor(private val bookRepository: BookRepository) : ViewModel() {

    fun addBook(book: Book) {
        bookRepository.addBook(book)
    }

    fun editBook(book: Book) {
        bookRepository.editBook(book)
    }
}