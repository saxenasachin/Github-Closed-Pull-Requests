package com.sachinsaxena.common.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
Created by Sachin Saxena on 16/06/22.
 */
abstract class BaseBindingFragment<VM : BaseViewModel<*>, VB : ViewBinding> :
    Fragment() {

    lateinit var viewModel: VM

    private var _binding: VB? = null

    val mBinding get() = _binding

    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = provideViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = provideViewBinding(inflater, container)
        return binding.root
    }

    protected open fun setupObservers() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provideActivityViewModel()
        setupObservers()
        setupView(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun goBack() {
        if (activity is BaseBindingActivity<*, *>) (activity as BaseBindingActivity<*, *>).goBack()
    }

    fun openKeyboard() {
        if (activity is BaseBindingActivity<*, *>) (activity as BaseBindingActivity<*, *>).openKeyboard()
    }

    fun closeKeyboard() {
        if (activity is BaseBindingActivity<*, *>) (activity as BaseBindingActivity<*, *>).closeKeyboard()
    }

    protected abstract fun provideViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    protected abstract fun providePageName(): String

    protected abstract fun provideViewModel(): VM

    protected open fun provideActivityViewModel() {}

    protected abstract fun setupView(view: View, savedInstanceState: Bundle?)

    val isViewModelInitialized get() = ::viewModel.isInitialized

}