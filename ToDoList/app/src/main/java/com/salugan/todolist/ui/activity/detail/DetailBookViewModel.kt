package com.salugan.todolist.ui.activity.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.salugan.todolist.data.BookRepository
import com.salugan.todolist.data.Result
import com.salugan.todolist.model.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailBookViewModel @Inject constructor(private val bookRepository: BookRepository) : ViewModel() {

    fun getSpecificBook(bookId: String): LiveData<Result<Book>>{
        return bookRepository.getSpecificBook(bookId)
    }

    fun deleteBook(book: Book) {
        bookRepository.deleteBook(book)
    }
}