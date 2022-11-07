package id.heycoding.storysubmission.data.remote.response.stories

data class StoryListResponse(
    val error: Boolean,
    val message: String,
    val listStory: List<StoryItem>
)
data class AddStoryResponse(
    val error: Boolean,
    val message: String
)