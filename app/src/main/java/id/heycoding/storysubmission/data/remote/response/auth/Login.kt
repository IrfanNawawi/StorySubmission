package id.heycoding.storysubmission.data.remote.response.auth

data class UserLoginResult(
    var name: String,
    var token: String,
    var userId: String
)

data class UserLoginResponse(
    val error: Boolean,
    val loginResult: UserLoginResult,
    val message: String
)