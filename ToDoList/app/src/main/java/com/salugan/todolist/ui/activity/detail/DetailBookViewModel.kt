package com.salugan.todolist.ui.activity.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.salugan.todolist.data.BookRepository
import com.salugan.todolist.data.Result
import com.salugan.todolist.model.Book

class DetailBookViewModel(private val bookRepository: BookRepository) : ViewModel() {

    fun getSpecificBook(bookId: String): LiveData<Result<Book>>{
        return bookRepository.getSpecificBook(bookId)
    }

    fun deleteBook(book: Book) {
        bookRepository.deleteBook(book)
    }
}