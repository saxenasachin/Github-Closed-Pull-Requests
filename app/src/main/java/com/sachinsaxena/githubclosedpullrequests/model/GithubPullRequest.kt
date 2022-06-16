package com.sachinsaxena.githubclosedpullrequests.model

import com.google.gson.annotations.SerializedName

/**
Created by Sachin Saxena on 16/06/22.
 */
data class GithubPullRequest(

    @SerializedName("author_association")
    val authorAssociation: String? = "",

    @SerializedName("body")
    val body: String? = "",

    @SerializedName("closed_at")
    val closedAt: String? = "",

    @SerializedName("comments_url")
    val commentsUrl: String? = "",

    @SerializedName("commits_url")
    val commitsUrl: String? = "",

    @SerializedName("created_at")
    val createdAt: String? = "",

    @SerializedName("diff_url")
    val diffUrl: String? = "",

    @SerializedName("draft")
    val draft: Boolean? = false,

    @SerializedName("html_url")
    val htmlUrl: String? = "",

    @SerializedName("id")
    val id: Int? = 0,

    @SerializedName("issue_url")
    val issueUrl: String? = "",

    @SerializedName("locked")
    val locked: Boolean? = false,

    @SerializedName("merge_commit_sha")
    val mergeCommitSha: String? = "",

    @SerializedName("merged_at")
    val mergedAt: String? = "",

    @SerializedName("node_id")
    val nodeId: String? = "",

    @SerializedName("number")
    val number: Int? = 0,

    @SerializedName("patch_url")
    val patchUrl: String? = "",

    @SerializedName("review_comment_url")
    val reviewCommentUrl: String? = "",

    @SerializedName("review_comments_url")
    val reviewCommentsUrl: String? = "",

    @SerializedName("state")
    val state: String? = "",

    @SerializedName("statuses_url")
    val statusesUrl: String? = "",

    @SerializedName("title")
    val title: String? = "",

    @SerializedName("updated_at")
    val updatedAt: String? = "",

    @SerializedName("url")
    val url: String? = "",

    @SerializedName("user")
    val user: GithubUser? = GithubUser()
) {
    enum class State(val value: String) {
        OPEN("open"), CLOSED("closed"), ALL("all")
    }
}
