package com.sachinsaxena.common.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
Created by Sachin Saxena on 16/06/22.
 */
abstract class BaseBindingActivity<VM : BaseViewModel<*>, VB : ViewBinding>
    : AppCompatActivity() {

    lateinit var viewModel: VM

    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = provideViewModel()
        binding = provideViewBinding()
        setContentView(binding.root)
        setupObservers()
        setupView(savedInstanceState)
    }

    protected open fun setupObservers() {}

    fun openKeyboard() {
        currentFocus?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(currentFocus, InputMethodManager.SHOW_IMPLICIT)
            imm.showSoftInput(currentFocus, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    fun closeKeyboard() {
        currentFocus?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
        }
    }

    open fun goBack() = onBackPressed()

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
        } else {
            super.onBackPressed()
        }
    }

    protected abstract fun provideViewBinding(): VB

    protected abstract fun providePageName(): String

    protected abstract fun provideViewModel(): VM

    protected abstract fun setupView(savedInstanceState: Bundle?)

    protected fun enforceLtrLayout() {
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_LTR
    }
}