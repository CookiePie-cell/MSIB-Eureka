package com.salugan.todolist.data

import androidx.lifecycle.LiveData
import com.salugan.todolist.data.remote.FirebaseDataSource
import com.salugan.todolist.model.Book

class BookRepository(private val firebaseDataSource: FirebaseDataSource) {

    fun getListBook(): LiveData<Result<List<Book>>> {
        return firebaseDataSource.getListBook()
    }

    fun getSpecificBook(bookId: String): LiveData<Result<Book>> {
        return firebaseDataSource.getSpecificBook(bookId)
    }

    fun addBook(book: Book) {
        firebaseDataSource.addBook(book)
    }

    fun editBook(book: Book) {
        firebaseDataSource.editBook(book)
    }

    fun deleteBook(book: Book) {
        firebaseDataSource.deleteBook(book)
    }
}