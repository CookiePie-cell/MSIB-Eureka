package com.salugan.githubuser.ui.activities.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salugan.githubuser.data.Result
import com.salugan.githubuser.data.UserRepository
import com.salugan.githubuser.data.remote.model.responses.DetailUserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    private val _detailUserResult = MutableStateFlow<Result<DetailUserResponse>>(Result.Loading)
    val detailUserResult: StateFlow<Result<DetailUserResponse>> = _detailUserResult.asStateFlow()

    private var username: String? = null

    fun setDetailUser(username: String) {
        if (this.username == null) {
            viewModelScope.launch {
                userRepository.getDetailUser(username)
                    .onStart { Result.Loading }
                    .collect { result ->
                        _detailUserResult.value = result
                    }
            }
        }
        this.username = username
    }
}