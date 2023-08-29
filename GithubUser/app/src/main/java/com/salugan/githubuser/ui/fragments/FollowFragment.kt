package com.salugan.githubuser.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.kennyc.view.MultiStateView
import com.salugan.githubuser.adapters.ListUserAdapter
import com.salugan.githubuser.data.remote.model.responses.UserResponse
import com.salugan.githubuser.databinding.FragmentFollowBinding
import com.salugan.githubuser.data.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

enum class FollowType {
    FOLLOWERS, FOLLOWING
}

@AndroidEntryPoint
class FollowFragment : Fragment(), MultiStateView.StateListener {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private var _multiStateView: MultiStateView? = null
    private val multiStateView get() = _multiStateView!!

    private val followViewModel: FollowViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _multiStateView = binding.fragmentFollow
        multiStateView.listener = this

        val position = arguments?.getInt(ARG_POSITION)
        val username = arguments?.getString(ARG_USERNAME)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollow.layoutManager = layoutManager

        val followType = if (position == 0) FollowType.FOLLOWERS else FollowType.FOLLOWING

        if (username != null) {
            handleFollowData(username, followType)
        }
    }

    private fun handleFollowData(username: String, followType: FollowType) {
        val followDataFlow = when (followType) {
            FollowType.FOLLOWERS -> {
                followViewModel.setFollowers(username)
                followViewModel.followers
            }
            FollowType.FOLLOWING -> {
                followViewModel.setFollowing(username)
                followViewModel.following
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                followDataFlow.collect { result ->
                    when (result) {
                        is Result.Loading -> multiStateView.viewState = MultiStateView.ViewState.LOADING
                        is Result.Success -> {
                            val followData = result.data
                            if (followData.isEmpty()) {
                                multiStateView.viewState = MultiStateView.ViewState.EMPTY
                            } else {
                                multiStateView.viewState = MultiStateView.ViewState.CONTENT
                                setRecyclerViews(followData)

                            }
                        }
                        is Result.Error -> multiStateView.viewState = MultiStateView.ViewState.ERROR
                    }
                }
            }
        }
    }


    private fun setRecyclerViews(users: List<UserResponse>) {
        val listUsers = ArrayList<UserResponse>()
        for (user in users) {
            listUsers.add(user)
        }
        val adapter = ListUserAdapter(listUsers)
        binding.rvFollow.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        _multiStateView
    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }

    override fun onStateChanged(viewState: MultiStateView.ViewState) {
        Log.v("MSVSample", "onStateChanged; viewState: $viewState")
    }
}