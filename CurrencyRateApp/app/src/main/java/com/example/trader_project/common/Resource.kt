package com.example.trader_project.common

sealed interface Resource<out T : Any> {
    // Başarılı durum
    data class Success<out T : Any>(val data: T) : Resource<T>

    // Hatalı durumu
    data class Error(val errorMessage: String) : Resource<Nothing>

    // Başarısız durumu
    data class Fail(val failMessage: String) : Resource<Nothing>
}