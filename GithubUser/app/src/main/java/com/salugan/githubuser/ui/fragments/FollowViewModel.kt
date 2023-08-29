package com.salugan.githubuser.ui.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salugan.githubuser.data.Result
import com.salugan.githubuser.data.UserRepository
import com.salugan.githubuser.data.remote.model.responses.UserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val _followers = MutableStateFlow<Result<List<UserResponse>>>(Result.Loading)
    val followers: StateFlow<Result<List<UserResponse>>> = _followers.asStateFlow()

    private val _following = MutableStateFlow<Result<List<UserResponse>>>(Result.Loading)
    val following: StateFlow<Result<List<UserResponse>>> = _following.asStateFlow()

    private var username: String? = null

    fun setFollowers(username: String) {
        if (this.username == null) {
            viewModelScope.launch {
                userRepository.getFollowers(username)
                    .onStart { Result.Loading }
                    .collect { result ->
                        _followers.value = result
                    }
            }
        }
        this.username = username
    }

    fun setFollowing(username: String) {
        if (this.username == null) {
            viewModelScope.launch {
                userRepository.getFollowing(username)
                    .onStart { Result.Loading }
                    .collect { result ->
                        _following.value = result
                    }
            }
        }
        this.username = username
    }
}