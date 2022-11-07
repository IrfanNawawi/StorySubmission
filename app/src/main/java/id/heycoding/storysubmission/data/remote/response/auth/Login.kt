package id.heycoding.storysubmission.data.remote.response.auth

data class UserLoginResult(
    var name: String,
    var token: String,
    var userId: String
)

data class UserLoginResponse(
    val loginResult: UserLoginResult,
)