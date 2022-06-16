package com.sachinsaxena.common.extensions

/**
Created by Sachin Saxena on 16/06/22.
 */
fun String?.isValid(): Boolean {
    return this != null && this.isNotEmpty() && this.isNotBlank()
}