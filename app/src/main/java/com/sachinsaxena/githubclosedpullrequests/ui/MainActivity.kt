package com.sachinsaxena.githubclosedpullrequests.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.sachinsaxena.common.extensions.addFragment
import com.sachinsaxena.common.extensions.addFragmentWithBackStack
import com.sachinsaxena.common.extensions.gone
import com.sachinsaxena.common.extensions.visible
import com.sachinsaxena.githubclosedpullrequests.R
import com.sachinsaxena.common.base.BaseBindingActivity
import com.sachinsaxena.githubclosedpullrequests.databinding.ActivityMainBinding
import com.sachinsaxena.githubclosedpullrequests.presentation.GitDataState

class MainActivity : BaseBindingActivity<MainViewModel, ActivityMainBinding>() {

    companion object {
        private const val TAG = "MainActivity"
        fun getStartIntent(context: Context): Intent =
            Intent(context, MainActivity::class.java)
    }

    override fun provideViewBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)

    override fun providePageName(): String = TAG

    override fun provideViewModel(): MainViewModel =
        ViewModelProvider(this)[MainViewModel::class.java]

    override fun setupView(savedInstanceState: Bundle?) {
        loadLandingFragment()
    }

    private fun loadLandingFragment() {
        addFragment(R.id.mainFragmentContainer, LandingFragment.newInstance().apply {
            setButtonClickListener(object : LandingFragment.OnButtonClickListener {
                override fun onClick(userName: String, repoName: String) {
                    addFragmentWithBackStack(
                        R.id.mainFragmentContainer, PullRequestsFragment.newInstance(userName, repoName),
                        PullRequestsFragment.TAG
                    )
                }
            })
        })
    }

    override fun setupObservers() {
        super.setupObservers()
        viewModel.stateObservable.observe(this) {
            updateState(it)
        }
    }

    private fun updateState(state: GitDataState) {
        when (state) {
            is GitDataState.Loading -> showLoading(state.message)
            else -> hideLoading()
        }
    }

    private fun showLoading(message: String) {
        binding.apply {
            tvLoadingMessage.text = message
            tvLoadingMessage.visible()
            loadingProgressbar.visible()
            mainFragmentContainer.gone()
        }
    }

    private fun hideLoading() {
        binding.apply {
            tvLoadingMessage.gone()
            loadingProgressbar.gone()
            mainFragmentContainer.visible()
        }
    }
}