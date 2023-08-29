package com.salugan.githubuser.ui.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.snackbar.Snackbar
import com.kennyc.view.MultiStateView
import com.salugan.githubuser.R
import com.salugan.githubuser.adapters.ListUserAdapter
import com.salugan.githubuser.data.remote.model.responses.UserResponse
import com.salugan.githubuser.databinding.ActivityMainBinding
import com.salugan.githubuser.data.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MultiStateView.StateListener {

    private lateinit var activityMainBinding: ActivityMainBinding

    private lateinit var multiStateView: MultiStateView

    private  var container: ShimmerFrameLayout? = null

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        supportActionBar?.title = "Github Users"

        multiStateView = activityMainBinding.activityMain
        multiStateView.listener = this

        container = activityMainBinding.activityMain.getView(MultiStateView.ViewState.LOADING)
            ?.findViewById(R.id.shimmer_container)


        val layoutManager = LinearLayoutManager(this)
        activityMainBinding.rvUsers.layoutManager = layoutManager

        mainViewModel.getListUsers()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.searchResult.collect { result ->
                    when (result) {
                        is Result.Loading -> {
                            multiStateView.viewState = MultiStateView.ViewState.LOADING
                        }
                        is Result.Success -> {
                            val users = result.data
                            if (users.isEmpty()) {
                                multiStateView.viewState = MultiStateView.ViewState.EMPTY
                            } else {
                                multiStateView.viewState = MultiStateView.ViewState.CONTENT
                                setListUsers(users)
                            }
                            stopShimmer()
                        }
                        is Result.Error -> {
                            multiStateView.viewState = MultiStateView.ViewState.ERROR
                            stopShimmer()
                            Snackbar.make(
                                window.decorView.rootView,
                                result.error,
                                Snackbar.LENGTH_INDEFINITE
                            ).setAction("RETRY") {
                                mainViewModel.getListUsers()
                            }.setActionTextColor(ContextCompat.getColor(this@MainActivity, R.color.github_green))
                                .show()
                        }
                    }
                }
            }
        }
    }


    private fun stopShimmer() {
        if (container != null) {
            container?.stopShimmer()
        }
    }


    private fun setListUsers(users: List<UserResponse>) {
        val listUsers = ArrayList<UserResponse>()
        for (user in users) {
            listUsers.add(user)
        }
        val adapter = ListUserAdapter(listUsers)
        activityMainBinding.rvUsers.adapter = adapter
    }


    override fun onStateChanged(viewState: MultiStateView.ViewState) {
    }
}