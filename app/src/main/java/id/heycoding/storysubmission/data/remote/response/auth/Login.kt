package id.heycoding.storysubmission.data.remote.response.auth

data class UserLoginResult(
    var name: String,
    var token: String,
    var userId: String
)

data class UserLoginResponse(
    var error: Boolean,
    var loginResult: UserLoginResult,
    var message: String
)