package com.sachinsaxena.githubclosedpullrequests.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sachinsaxena.common.extensions.isValid
import com.sachinsaxena.githubclosedpullrequests.R
import com.sachinsaxena.githubclosedpullrequests.databinding.LayoutSinglePullRequestItemBinding
import com.sachinsaxena.githubclosedpullrequests.model.GithubPullRequest
import com.squareup.picasso.Picasso

/**
Created by Sachin Saxena on 16/06/22.
 */
class PullRequestListAdaptor(private val listOfPrs: List<GithubPullRequest>) :
    RecyclerView.Adapter<PullRequestListAdaptor.PullRequestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PullRequestViewHolder {
        return PullRequestViewHolder(
            LayoutSinglePullRequestItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return listOfPrs.size
    }

    override fun onBindViewHolder(holder: PullRequestViewHolder, position: Int) {
        holder.bind(listOfPrs[position])
    }

    inner class PullRequestViewHolder(private val binding: LayoutSinglePullRequestItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pullRequestView: GithubPullRequest) {
            binding.tvPrTitle.text = pullRequestView.title

            pullRequestView.user?.avatarUrl?.let {
                if (it.isValid())
                    Picasso.get().load(it).into(binding.ivUserProfile)
            }

            binding.tvUserName.text =
                binding.root.context.getString(R.string.label_by).plus(" ")
                    .plus(pullRequestView.user?.login)

            binding.tvCreatedAt.text = pullRequestView.createdAt
            binding.tvClosedAt.text = pullRequestView.closedAt
        }
    }
}