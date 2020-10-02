package nl.bouwman.marc.news.ui

import android.content.Context
import androidx.lifecycle.MutableLiveData
import nl.bouwman.marc.news.domain.models.Result
import nl.bouwman.marc.news.domain.services.NewsReaderApi
import nl.bouwman.marc.news.ui.utils.defaultEncryptedPreferences
import nl.bouwman.marc.news.ui.utils.edit
import org.koin.core.KoinComponent

class AccountManagerImpl(
    private val api: NewsReaderApi,
    context: Context
) : AccountManager, KoinComponent {
    private var token: String? = null

    override val isLoggedIn = MutableLiveData(false)

    private val preferences by lazy {
        context.defaultEncryptedPreferences
    }

    override suspend fun fetchToken(): String? {
        if (token == null) {
            // Check if we can re-use our saved token
            val savedToken = preferences.getString(SETTINGS_TOKEN, null)
            if (savedToken != null && api.isTokenValid(savedToken)) {
                token = savedToken
                isLoggedIn.postValue(true)
                return token
            }

            // Check if we can login again
            val username = preferences.getString(SETTINGS_USERNAME, null)
            val password = preferences.getString(SETTINGS_PASSWORD, null)
            if (username != null && password != null) {
                login(username, password)
            }
        }

        return token
    }

    override suspend fun createAccount(username: String, password: String): Result<String, String?> {
        val response = api.registerAccount(username, password)
        if (response is Result.OK)
            login(username, password)
        return response
    }

    override suspend fun login(username: String, password: String): Boolean {
        token = api.login(username, password)
        val loginSuccessful = token != null
        isLoggedIn.postValue(loginSuccessful)
        preferences.edit {
            if (loginSuccessful) {
                putString(SETTINGS_TOKEN, token)
                putString(SETTINGS_USERNAME, username)
                putString(SETTINGS_PASSWORD, password)
            } else {
                remove(SETTINGS_TOKEN)
                remove(SETTINGS_USERNAME)
                remove(SETTINGS_PASSWORD)
            }
        }
        return loginSuccessful
    }

    override suspend fun logout() {
        isLoggedIn.postValue(false)
        token = null
        preferences.edit {
            remove(SETTINGS_TOKEN)
            remove(SETTINGS_USERNAME)
            remove(SETTINGS_PASSWORD)
        }
    }

    companion object {
        const val SETTINGS_TOKEN = "TOKEN"
        const val SETTINGS_USERNAME = "USERNAME"
        const val SETTINGS_PASSWORD = "PASSWORD"
    }
}