package id.heycoding.storysubmission.data.remote.response.stories

import com.google.gson.annotations.SerializedName
import id.heycoding.storysubmission.data.local.entity.Story

data class StoryListResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("listStory")
    val listStory: List<Story>
)

data class AddStoryResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)