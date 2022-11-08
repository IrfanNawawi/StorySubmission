package id.heycoding.storysubmission.data.remote.response.auth

data class AuthSession(
    val name: String,
    val token: String,
    val userId: String,
    val isLogin: Boolean
)