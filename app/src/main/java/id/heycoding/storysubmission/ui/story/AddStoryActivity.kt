package id.heycoding.storysubmission.ui.story

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import id.heycoding.storysubmission.R
import id.heycoding.storysubmission.data.remote.WebServices
import id.heycoding.storysubmission.data.remote.response.auth.UserLoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddStoryActivity : AppCompatActivity() {
    private val tvLog: TextView by lazy { findViewById(R.id.testing) }
    lateinit var user : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_story)

        tvLog.setOnClickListener {
            val services = WebServices.create()
            services.loginUser("irfnawi@gmail.com", "123456")
                .enqueue(object : Callback<UserLoginResponse> {
                    override fun onResponse(
                        call: Call<UserLoginResponse>,
                        response: Response<UserLoginResponse>
                    ) {
                        if (!response.isSuccessful) {
                            tvLog.text = response.message()
                        }

                        if (it != null) {
                            tvLog.text = response.body()?.loginResult?.name
                        } else {
                            tvLog.text = "Kosong coy"
                        }
                    }

                    override fun onFailure(call: Call<UserLoginResponse>, t: Throwable) {
                        tvLog.text = t.message
                        Log.i("LoginFragment", "subscribe: gagal lah")
                    }
                })
        }
    }
}