package nl.bouwman.marc.news.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import nl.bouwman.marc.news.domain.services.AccountManager

class SplashScreenViewModel : ViewModel() {
    private val mutableShouldShowSplashScreen = MutableLiveData(true)

    val shouldShowSplashScreen: LiveData<Boolean>
        get() = mutableShouldShowSplashScreen

    init {
        viewModelScope.launch {
            delay(1000)
            mutableShouldShowSplashScreen.postValue(false)
        }
    }
}