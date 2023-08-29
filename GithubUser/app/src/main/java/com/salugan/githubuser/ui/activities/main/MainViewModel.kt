package com.salugan.githubuser.ui.activities.main

import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope

import com.salugan.githubuser.data.UserRepository
import com.salugan.githubuser.data.remote.model.responses.UserResponse
import com.salugan.githubuser.data.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val _searchResult = MutableStateFlow<Result<List<UserResponse>>>(Result.Loading)
    val searchResult: StateFlow<Result<List<UserResponse>>> = _searchResult.asStateFlow()

    init {
        getListUsers()
    }

    fun getListUsers() {
        viewModelScope.launch {
            userRepository.getListUsers()
                .onStart { Result.Loading }
                .collect { result ->
                    _searchResult.value = result
                }
        }
    }
}