package id.heycoding.storysubmission.data.remote

import id.heycoding.storysubmission.data.remote.response.BaseResponse
import io.reactivex.Observable
import retrofit2.Response

typealias StoryResponse<T> = Observable<Response<BaseResponse<T>>>