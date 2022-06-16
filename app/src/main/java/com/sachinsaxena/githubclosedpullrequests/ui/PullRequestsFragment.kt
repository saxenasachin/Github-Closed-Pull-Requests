package com.sachinsaxena.githubclosedpullrequests.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.androchef.githubsampleapp.extensions.toast
import com.sachinsaxena.githubclosedpullrequests.R
import com.sachinsaxena.githubclosedpullrequests.base.BaseBindingFragment
import com.sachinsaxena.githubclosedpullrequests.databinding.FragmentPullRequestsBinding
import com.sachinsaxena.githubclosedpullrequests.model.GithubPullRequest
import com.sachinsaxena.githubclosedpullrequests.presentation.GitDataState

/**
Created by Sachin Saxena on 16/06/22.
 */
class PullRequestsFragment : BaseBindingFragment<MainViewModel, FragmentPullRequestsBinding>() {

    companion object {
        const val TAG = "PullRequestsFragment"
        private const val ARGS_USERNAME = "args_username"
        private const val ARGS_REPO_NAME = "args_repo_name"
        fun newInstance(userName: String, repoName: String): PullRequestsFragment {
            val bundle = Bundle()
            bundle.putString(ARGS_USERNAME, userName)
            bundle.putString(ARGS_REPO_NAME, repoName)
            val fragment = PullRequestsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val userName: String? by lazy { arguments?.getString(ARGS_USERNAME) }

    private val repoName: String? by lazy { arguments?.getString(ARGS_REPO_NAME) }

    private fun fetchPullRequests() {
        //Config for all states [open, closed, all].
        viewModel.getGithubPullRequests(
            userName.orEmpty(),
            repoName.orEmpty(),
            GithubPullRequest.State.CLOSED.value
        )
    }

    override fun setupObservers() {
        super.setupObservers()
        viewModel.stateObservable.observe(this) {
            updateState(it)
        }
    }

    private fun updateState(state: GitDataState) {
        when (state) {
            is GitDataState.GetPullRequestsSuccess -> showPrList(state.lisOfPullRequests)
            is GitDataState.Error -> {
                popUpFragment(state.message)
            }
            else -> {}
        }
    }

    private fun showPrList(listOfPullRequests: List<GithubPullRequest>) {
        if (listOfPullRequests.isNotEmpty()) {
            mBinding?.pullRequestRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
            mBinding?.pullRequestRecyclerView?.adapter = PullRequestListAdaptor(listOfPullRequests)
        } else {
            popUpFragment(getString(R.string.info_no_pr_found))
        }
    }

    private fun popUpFragment(message: String) {
        viewModel.resetState()
        toast(message)
        childFragmentManager.popBackStack()
    }


    override fun provideViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPullRequestsBinding =
        FragmentPullRequestsBinding.inflate(layoutInflater, container, false)

    override fun providePageName(): String = TAG

    override fun provideViewModel(): MainViewModel =
        ViewModelProvider(requireActivity())[MainViewModel::class.java]

    override fun setupView(view: View, savedInstanceState: Bundle?) {
        fetchPullRequests()
    }
}