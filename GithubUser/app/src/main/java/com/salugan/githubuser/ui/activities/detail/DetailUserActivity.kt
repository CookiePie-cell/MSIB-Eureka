package com.salugan.githubuser.ui.activities.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kennyc.view.MultiStateView
import com.salugan.githubuser.R
import com.salugan.githubuser.adapters.SectionsPagerAdapter
import com.salugan.githubuser.data.remote.model.responses.DetailUserResponse
import com.salugan.githubuser.databinding.ActivityDetailUserBinding
import com.salugan.githubuser.data.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailUserActivity : AppCompatActivity(), MultiStateView.StateListener {

    private lateinit var activityDetailUserBinding: ActivityDetailUserBinding

    private lateinit var multiStateView: MultiStateView

    private val detailViewModel: DetailViewModel by viewModels()

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        const val EXTRA_USERNAME = "extra_username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailUserBinding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(activityDetailUserBinding.root)

        supportActionBar?.title = "Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        multiStateView = activityDetailUserBinding.userDetailInfo.detailShimmer
        multiStateView.listener = this

        val username = intent.getStringExtra(EXTRA_USERNAME)

        if (username != null) {
            detailViewModel.setDetailUser(username)
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    detailViewModel.detailUserResult.collect { result ->
                        when (result) {
                            is Result.Loading -> {
                                multiStateView.viewState = MultiStateView.ViewState.LOADING
                            }
                            is Result.Success -> {
                                multiStateView.viewState = MultiStateView.ViewState.CONTENT
                                setDetailView(result.data)
                            }
                            is Result.Error -> {
                                Snackbar.make(
                                    window.decorView.rootView,
                                    result.error,
                                    Snackbar.LENGTH_INDEFINITE
                                ).show()
                            }
                        }
                    }
                }
            }

            val sectionsPagerAdapter = SectionsPagerAdapter(this)
            sectionsPagerAdapter.username = username
            val viewPager: ViewPager2 = activityDetailUserBinding.viewPager
            viewPager.adapter = sectionsPagerAdapter
            val tabs: TabLayout = activityDetailUserBinding.tabs
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }

    }

    private fun setDetailView(detailUser: DetailUserResponse) {
        activityDetailUserBinding.apply {
            userDetailInfo.detailUsername.text = detailUser.login
            userDetailInfo.detailName.text = detailUser.name
            userDetailInfo.detailEmail.text = if ((detailUser.email).isNullOrEmpty()) {
                "No email"
            } else detailUser.email
            userDetailInfo.company.text = detailUser.company
        }


        Glide.with(this@DetailUserActivity)
            .load(detailUser.avatarUrl)
            .into(activityDetailUserBinding.userDetailInfo.userAvatar)
    }


    override fun onStateChanged(viewState: MultiStateView.ViewState) {
        Log.v("MSVSample", "onStateChanged; viewState: $viewState")
    }
}