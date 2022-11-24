package id.heycoding.storysubmission.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.heycoding.storysubmission.data.local.entity.Story
import id.heycoding.storysubmission.data.remote.WebServices
import id.heycoding.storysubmission.data.remote.repository.Repository
import id.heycoding.storysubmission.data.remote.response.stories.StoryItem
import id.heycoding.storysubmission.data.remote.response.stories.StoryListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(repository: Repository): ViewModel() {
    val listStoryData: LiveData<PagingData<Story>> = repository.storyList
    val isLoading : LiveData<Boolean> = repository.isLoading
    val message : LiveData<String> = repository.message
}