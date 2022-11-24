package id.heycoding.storysubmission.data.remote

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import id.heycoding.storysubmission.data.remote.response.auth.UserLoginResponse
import id.heycoding.storysubmission.data.remote.response.auth.UserRegisterResponse
import id.heycoding.storysubmission.data.remote.response.stories.AddStoryResponse
import id.heycoding.storysubmission.data.remote.response.stories.StoryListResponse
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface WebServices {

    @FormUrlEncoded
    @POST(EndPoint.User.LOGIN)
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<UserLoginResponse>

    @FormUrlEncoded
    @POST(EndPoint.User.REGISTER)
    fun registerUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<UserRegisterResponse>

    @GET(EndPoint.Stories.STORIES)
    fun getAllStories(
        @Header("Authorization") auth: String
    ): Call<StoryListResponse>

    @Multipart
    @POST(EndPoint.Stories.STORIES)
    fun uploadStory(
        @Header("Authorization") auth: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Call<AddStoryResponse>

    @GET(EndPoint.Stories.STORIES)
    fun getAllStoriesWithLocation(
        @Header("Authorization") auth: String,
        @Query("location")location: Int
    ): Call<StoryListResponse>

    @Multipart
    @POST(EndPoint.Stories.STORIES)
    fun uploadStoriesWithLocation(
        @Header("Authorization") auth: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat")latitude: Float,
        @Part("lon")longitude: Float
    ): Call<AddStoryResponse>

    object EndPoint {
        const val BASE_URL = "https://story-api.dicoding.dev/v1/"

        object User {
            const val LOGIN = "login"
            const val REGISTER = "register"
        }

        object Stories {
            const val STORIES = "stories"
        }
    }
}