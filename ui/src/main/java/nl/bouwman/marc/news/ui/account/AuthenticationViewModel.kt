package nl.bouwman.marc.news.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nl.bouwman.marc.news.domain.models.Result
import nl.bouwman.marc.news.domain.services.AccountManager

class AuthenticationViewModel(
    private val accountManager: AccountManager
) : ViewModel() {
    private val mutableIsLoading = MutableLiveData(false)

    val isLoggedIn
        get() = accountManager.isLoggedIn

    val isLoading: LiveData<Boolean>
        get() = mutableIsLoading

    private val mutableErrorMessage = MutableLiveData<String?>(null)

    val errorMessage: LiveData<String?>
        get() = mutableErrorMessage

    fun createAccount(username: String, password: String) {
        mutableIsLoading.postValue(true)

        viewModelScope.launch {
            val result = accountManager.createAccount(username, password)
            mutableErrorMessage.postValue(if(result is Result.Error) result.error else null)

            mutableIsLoading.postValue(false)
        }
    }

    fun login(username: String, password: String) {
        mutableIsLoading.postValue(true)

        viewModelScope.launch {
            val result = accountManager.login(username, password)
            mutableErrorMessage.postValue(if(!result) LOGIN_ERROR_MESSAGE else null)

            mutableIsLoading.postValue(false)
        }
    }

    companion object {
        const val LOGIN_ERROR_MESSAGE = "LOGIN_ERROR_MESSAGE"
    }
}