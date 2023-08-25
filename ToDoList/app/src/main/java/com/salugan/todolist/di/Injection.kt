package com.salugan.todolist.di

import com.salugan.todolist.data.BookRepository
import com.salugan.todolist.data.remote.FirebaseDataSource

object Injection {
    fun provideRepository(): BookRepository {
        val dataSource = provideDataSource()
        return BookRepository(dataSource)
    }

    fun provideDataSource() = FirebaseDataSource()
}