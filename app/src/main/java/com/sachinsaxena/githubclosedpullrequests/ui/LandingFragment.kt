package com.sachinsaxena.githubclosedpullrequests.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.androchef.githubsampleapp.extensions.addFragmentBackStack
import com.androchef.githubsampleapp.extensions.isValid
import com.androchef.githubsampleapp.extensions.toast
import com.sachinsaxena.githubclosedpullrequests.R
import com.sachinsaxena.githubclosedpullrequests.base.BaseBindingFragment
import com.sachinsaxena.githubclosedpullrequests.databinding.FragmentLandingBinding

/**
Created by Sachin Saxena on 16/06/22.
 */
class LandingFragment : BaseBindingFragment<MainViewModel, FragmentLandingBinding>() {

    companion object {
        private const val TAG = "LandingFragment"
        fun newInstance(): LandingFragment {
            return LandingFragment()
        }
    }

    private var onButtonClickListener: OnButtonClickListener? = null

    override fun provideViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLandingBinding =
        FragmentLandingBinding.inflate(layoutInflater, container, false)

    override fun providePageName(): String = TAG

    override fun provideViewModel(): MainViewModel =
        ViewModelProvider(requireActivity())[MainViewModel::class.java]

    override fun setupView(view: View, savedInstanceState: Bundle?) {
        setUpClickListener()
    }

    private fun setUpClickListener() {
        mBinding?.apply {
            btnContinue.setOnClickListener {
                val userName = edtUserName.editableText.toString()
                val repoName = edtRepoName.editableText.toString()
                if (!userName.isValid()) {
                    toast(getString(R.string.info_valid_username))
                    return@setOnClickListener
                }
                if (!repoName.isValid()) {
                    toast(getString(R.string.info_valid_repository_name))
                    return@setOnClickListener
                }
                goToNextFragment(userName, repoName)
            }
        }
    }

    private fun goToNextFragment(userName: String, repoName: String) {
        onButtonClickListener?.onClick(userName, repoName)
    }

    fun setButtonClickListener(onButtonClickListener: OnButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener
    }

    interface OnButtonClickListener {
        fun onClick(userName: String, repoName: String)
    }
}