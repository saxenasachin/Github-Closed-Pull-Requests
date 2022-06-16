package com.sachinsaxena.githubclosedpullrequests.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sachinsaxena.githubclosedpullrequests.base.BaseViewModel
import com.sachinsaxena.githubclosedpullrequests.model.GithubPullRequest
import com.sachinsaxena.githubclosedpullrequests.model.GithubSingleRepo
import com.sachinsaxena.githubclosedpullrequests.network.ApiInterface
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

    fun getGithubRepositories(userName: String) {
        state = GitDataState.Loading("Fetching $userName's repositories")
        viewModelScope.launch(Dispatchers.IO) {
            val apiInterface = ApiInterface.create().getUserRepositories(userName)
            apiInterface.enqueue(object : Callback<List<GithubSingleRepo>> {
                override fun onResponse(
                    call: Call<List<GithubSingleRepo>>,
                    response: Response<List<GithubSingleRepo>>
                ) {
                    val repositoryList = response.body().orEmpty()
                    state = GitDataState.GetUsersRepositoriesSuccess(repositoryList)
                }

                override fun onFailure(call: Call<List<GithubSingleRepo>>, t: Throwable) {
                    state = GitDataState.Error(t.localizedMessage.orEmpty())
                }
            })
        }
    }

    fun getGithubPullRequests(userName: String, repoName: String, prState: String) {
        state = GitDataState.Loading("Fetching $repoName's pull requests")
        viewModelScope.launch(Dispatchers.IO) {
            val apiInterface =
                ApiInterface.create().getPullRequestForGithubRepo(userName, repoName, prState)
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