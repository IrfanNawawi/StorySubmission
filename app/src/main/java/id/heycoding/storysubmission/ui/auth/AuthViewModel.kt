package id.heycoding.storysubmission.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.heycoding.storysubmission.data.remote.WebServices
import id.heycoding.storysubmission.data.remote.repository.Repository
import id.heycoding.storysubmission.data.remote.response.auth.UserLoginResponse
import id.heycoding.storysubmission.data.remote.response.auth.UserLoginResult
import id.heycoding.storysubmission.data.remote.response.auth.UserRegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val userLogin: LiveData<UserLoginResult> = repository.userLogin
    val isError: LiveData<Boolean> = repository.isError
    val message: LiveData<String> = repository.message
    val isLoading: LiveData<Boolean> = repository.isLoading

    fun doLogin(email: String, password: String) {
        repository.userLogin(email, password)
    }

    fun doRegister(name: String, email: String, password: String) {
        repository.userRegister(name, email, password)
    }
}