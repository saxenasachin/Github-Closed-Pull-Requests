package com.sachinsaxena.githubclosedpullrequests.presentation

import com.sachinsaxena.githubclosedpullrequests.model.GithubPullRequest

sealed class GitDataState {

    object Init : GitDataState()

    data class Loading(val message: String) : GitDataState()

    data class Error(val message: String) : GitDataState()

    data class GetPullRequestsSuccess(
        val lisOfPullRequests: List<GithubPullRequest>
    ) : GitDataState()
}