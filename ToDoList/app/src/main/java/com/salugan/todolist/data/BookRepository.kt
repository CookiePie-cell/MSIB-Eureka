package com.salugan.todolist.data

import androidx.lifecycle.LiveData
import com.salugan.todolist.data.remote.FirebaseDataSource
import com.salugan.todolist.model.Book

class BookRepository(private val firebaseDataSource: FirebaseDataSource) {

    fun getListBuku(): LiveData<Result<List<Book>>> {
        return firebaseDataSource.getListBook()
    }

    fun addBook(book: Book) {
        firebaseDataSource.addBook(book)
    }

    fun editBook(book: Book) {

    }

    fun deleteBook(book: Book) {

    }
}