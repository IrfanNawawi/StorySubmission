package id.heycoding.storysubmission.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.heycoding.storysubmission.data.remote.response.auth.AuthSession
import id.heycoding.storysubmission.utils.Preferences
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataStoreViewModel @Inject constructor(private val preferences: Preferences) : ViewModel() {
    fun getAuthSession(): LiveData<AuthSession> {
        return preferences.getLoginData().asLiveData()
    }

    fun setAuthSession(loggedUser: AuthSession) {
        viewModelScope.launch {
            preferences.setUserLogin(loggedUser)
        }
    }

    fun logout() {
        viewModelScope.launch {
            preferences.logout()
        }
    }
}