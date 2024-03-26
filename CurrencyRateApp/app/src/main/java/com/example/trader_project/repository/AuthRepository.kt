package com.example.trader_project.repository

import com.example.trader_project.common.Resource
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthRepository(private val firebaseAuth: FirebaseAuth) {

    fun isUserLoggedIn() = firebaseAuth.currentUser != null

    suspend fun signIn(email: String, password: String): Resource<Unit> {

        return try {

            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()

            if (result.user != null) {
                Resource.Success(Unit)
            } else {
                Resource.Error("Bir hata oluştu")
            }
        } catch (e: Exception) {
            Resource.Error("Böyle bir kullanıcı kaydı yok")
        }
    }

    suspend fun signUp(email: String, password: String): Resource<Unit> {

        return try {

            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()

            if (result.user != null) {
                Resource.Success(Unit)
            } else {
                Resource.Error("Bir hata oluştu, tekrar deneyiniz.")
            }
        } catch (e: Exception) {
            Resource.Error("Böyle bir kullanıcı kaydı var")
        }
    }

    fun logOut() = firebaseAuth.signOut()
}
