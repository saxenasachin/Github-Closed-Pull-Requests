package com.androchef.githubsampleapp.extensions

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
Created by Sachin Saxena on 16/06/22.
 */
inline fun <reified VM : ViewModel> FragmentActivity.viewModelProvider(
    provider: ViewModelProvider.Factory
) = ViewModelProvider(this, provider).get(VM::class.java)

fun AppCompatActivity.addFragment(layoutId: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction().add(layoutId, fragment).commit()
}

fun AppCompatActivity.addFragmentWithBackStack(layoutId: Int, fragment: Fragment, tag: String) {
    supportFragmentManager.beginTransaction().add(layoutId, fragment)
        .addToBackStack(tag).commit()
}

fun AppCompatActivity.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}