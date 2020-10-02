package nl.bouwman.marc.news.ui

import androidx.lifecycle.LiveData
import nl.bouwman.marc.news.domain.models.Result

interface AccountManager {
    val isLoggedIn: LiveData<Boolean>

    suspend fun fetchToken(): String?

    suspend fun createAccount(username: String, password: String): Result<String, String?>

    suspend fun login(username: String, password: String): Boolean

    suspend fun logout()
}