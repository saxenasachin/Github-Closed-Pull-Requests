package com.sachinsaxena.githubclosedpullrequests.network

import com.sachinsaxena.githubclosedpullrequests.model.GithubPullRequest
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
Created by Sachin Saxena on 17/06/22.
 */
interface GithubService {
    @GET("/repos/{username}/{repo_name}/pulls")
    fun getPullRequestForGithubRepo(
        @Path("username") username: String,
        @Path("repo_name") repoName: String,
        @Query("state") state: String = "all"
    ): Call<List<GithubPullRequest>>
}