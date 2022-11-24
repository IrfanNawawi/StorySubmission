package id.heycoding.storysubmission.data.remote.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import id.heycoding.storysubmission.data.local.entity.Story
import id.heycoding.storysubmission.data.remote.WebServices
import id.heycoding.storysubmission.data.remote.response.auth.UserLoginResponse
import id.heycoding.storysubmission.data.remote.response.auth.UserLoginResult
import id.heycoding.storysubmission.data.remote.response.auth.UserRegisterResponse
import id.heycoding.storysubmission.data.remote.response.stories.AddStoryResponse
import id.heycoding.storysubmission.data.remote.response.stories.StoryListResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class Repository @Inject constructor(
    private val services: WebServices
) {
    private val _userLogin = MutableLiveData<UserLoginResult>()
    val userLogin: LiveData<UserLoginResult> = _userLogin

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _storyList = MutableLiveData<List<Story>>()
    val storyList: LiveData<List<Story>> = _storyList

    fun userLogin(email: String, password: String) {
        _isLoading.value = true
        services.loginUser(email, password)
            .enqueue(object : Callback<UserLoginResponse> {
                override fun onResponse(
                    call: Call<UserLoginResponse>,
                    response: Response<UserLoginResponse>
                ) {
                    _isLoading.value = false
                    if (!response.isSuccessful) {
                        _message.value = response.message()
                    }
                    _isError.value = response.body()?.error
                    _message.value = response.body()?.message
                    _userLogin.value = response.body()?.loginResult
                }

                override fun onFailure(call: Call<UserLoginResponse>, t: Throwable) {
                    _message.value = t.message
                    _isLoading.value = false
                }
            })
    }

    fun userRegister(name: String, email: String, password: String) {
        _isLoading.value = true
        services.registerUser(name, email, password)
            .enqueue(object : Callback<UserRegisterResponse> {
                override fun onResponse(
                    call: Call<UserRegisterResponse>,
                    response: Response<UserRegisterResponse>
                ) {
                    _isLoading.value = false
                    if (!response.isSuccessful) {
                        _message.value = response.message()
                    }
                    _isError.value = response.body()?.error
                    _message.value = response.body()?.message
                }

                override fun onFailure(call: Call<UserRegisterResponse>, t: Throwable) {
                    _message.value = t.message
                    _isLoading.value = false
                }

            })
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getStoriesData(): LiveData<PagingData<Story>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase, service, pref),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllStory()
            }
        ).liveData
    }

    fun uploadStoryLocation(
        token: String,
        picture: File,
        storyDesc: String,
        userLat: Float,
        userLong: Float
    ) {
        val description = storyDesc.toRequestBody("text/plain".toMediaType())
        val pictureFile = picture.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            picture.name,
            pictureFile
        )
        services.uploadStoriesWithLocation(
            token,
            imageMultipart,
            description,
            userLat,
            userLong
        ).enqueue(object : Callback<AddStoryResponse> {
            override fun onResponse(
                call: Call<AddStoryResponse>,
                response: Response<AddStoryResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        _message.value = responseBody.message
                    } else {
                        _message.value = response.message()
                    }
                }
            }

            override fun onFailure(call: Call<AddStoryResponse>, t: Throwable) {
                _message.value = t.message
            }

        })
    }

    fun uploadStory(token: String, picture: File, desc: String) {
        val description = desc.toRequestBody("text/plain".toMediaType())
        val pictureFile = picture.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            picture.name,
            pictureFile
        )
        val service = services.uploadStory(token, imageMultipart, description)
        service.enqueue(object : Callback<AddStoryResponse> {
            override fun onResponse(
                call: Call<AddStoryResponse>,
                response: Response<AddStoryResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        _message.value = responseBody.message
                    } else {
                        _message.value = response.message()
                    }
                }
            }

            override fun onFailure(call: Call<AddStoryResponse>, t: Throwable) {
                _message.value = t.message
            }
        })
    }

    fun getStoriesWithLocation(token: String) {
        services.getAllStoriesWithLocation(token, 1)
            .enqueue(object : Callback<StoryListResponse> {
                override fun onResponse(
                    call: Call<StoryListResponse>,
                    response: Response<StoryListResponse>
                ) {
                    if (response.isSuccessful) {
                        _storyList.postValue(response.body()?.listStory)
                        _message.postValue(response.body()?.message)
                    } else {
                        _message.postValue(response.message())
                    }
                }

                override fun onFailure(call: Call<StoryListResponse>, t: Throwable) {
                    _message.postValue(t.message)
                }
            })
    }
}