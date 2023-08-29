package com.salugan.todolist.di

import com.salugan.todolist.data.BookRepository
import com.salugan.todolist.data.remote.FirebaseDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseDataResource(): FirebaseDataSource =
        FirebaseDataSource()

    @Provides
    @Singleton
    fun provideBookRepository(firebaseDataSource: FirebaseDataSource): BookRepository =
        BookRepository(firebaseDataSource)
}