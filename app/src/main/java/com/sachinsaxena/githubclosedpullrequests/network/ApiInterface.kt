package com.sachinsaxena.githubclosedpullrequests.network

import com.sachinsaxena.githubclosedpullrequests.BuildConfig
import com.sachinsaxena.githubclosedpullrequests.model.GithubPullRequest
import com.sachinsaxena.githubclosedpullrequests.model.GithubSingleRepo
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
Created by Sachin Saxena on 16/06/22.
 */
interface ApiInterface {

    @GET("users/{username}/repos")
    fun getUserRepositories(
        @Path("username") username: String
    ): Call<List<GithubSingleRepo>>

    @GET("/repos/{username}/{repo_name}/pulls")
    fun getPullRequestForGithubRepo(
        @Path("username") username: String,
        @Path("repo_name") repoName: String,
        @Query("state") state: String = "all"
    ): Call<List<GithubPullRequest>>

    companion object {

        fun create() : ApiInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}