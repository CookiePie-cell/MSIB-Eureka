package com.salugan.githubuser.ui.activities.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.salugan.githubuser.data.UserRepository
import com.salugan.githubuser.di.Injection

class MainViewModelFactory private constructor(
    private val userRepository: UserRepository,
    ) : ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: MainViewModelFactory? = null
        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: MainViewModelFactory(Injection.provideRepository())
            }.also { instance = it }
    }
}