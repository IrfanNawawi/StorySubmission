package id.heycoding.storysubmission.data.remote.response

import com.google.gson.annotations.SerializedName

class BaseResponse<T>(
    @SerializedName("error")
    val error: Boolean? = false,
    @SerializedName("message")
    val message: String? = "",
    @SerializedName("data")
    val `data`: T? = null
)