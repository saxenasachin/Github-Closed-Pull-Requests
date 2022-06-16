package com.sachinsaxena.githubclosedpullrequests.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sachinsaxena.common.base.BaseViewModel
import com.sachinsaxena.githubclosedpullrequests.model.GithubPullRequest
import com.sachinsaxena.githubclosedpullrequests.network.GithubService
import com.sachinsaxena.githubclosedpullrequests.network.Network
import com.sachinsaxena.githubclosedpullrequests.presentation.GitDataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
Created by Sachin Saxena on 16/06/22.
 */
class MainViewModel : BaseViewModel<GitDataState>() {

    override val stateObservable: MutableLiveData<GitDataState> by lazy {
        MutableLiveData<GitDataState>()
    }

    private var state: GitDataState = GitDataState.Init
        set(value) {
            field = value
            publishState(value)
        }

    fun getGithubPullRequests(userName: String, repoName: String, prState: String) {
        state = GitDataState.Loading("Fetching $repoName's pull requests")
        viewModelScope.launch(Dispatchers.IO) {
            val apiInterface =
                Network.retrofit.create(GithubService::class.java)
                    .getPullRequestForGithubRepo(userName, repoName, prState)
            apiInterface.enqueue(object : Callback<List<GithubPullRequest>> {
                override fun onResponse(
                    call: Call<List<GithubPullRequest>>,
                    response: Response<List<GithubPullRequest>>
                ) {
                    val pullRequestList = response.body().orEmpty()
                    state = GitDataState.GetPullRequestsSuccess(pullRequestList)
                }

                override fun onFailure(call: Call<List<GithubPullRequest>>, t: Throwable) {
                    state = GitDataState.Error(t.localizedMessage.orEmpty())
                }
            })
        }
    }

    fun resetState() {
        state = GitDataState.Init
    }
}