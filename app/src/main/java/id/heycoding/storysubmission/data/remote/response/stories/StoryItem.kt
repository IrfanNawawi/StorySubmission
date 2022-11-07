package id.heycoding.storysubmission.data.remote.response.stories

data class StoryItem(
    val createdAt: String,
    val description: String,
    val id: String,
    val name: String,
    val photoUrl: String
)