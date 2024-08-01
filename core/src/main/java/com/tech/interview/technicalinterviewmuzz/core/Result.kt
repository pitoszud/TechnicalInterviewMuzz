package com.tech.interview.technicalinterviewmuzz.core

sealed class Result<out R> {
    object Loading : Result<Nothing>()
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val errorMessage: Int) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Loading -> "Loading..."
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[errorMessage=$errorMessage]"
        }
    }
}

val Result<*>.succeeded
    get() = this is Result.Success && data != null