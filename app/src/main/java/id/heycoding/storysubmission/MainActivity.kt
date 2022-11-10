package id.heycoding.storysubmission

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import id.heycoding.storysubmission.data.remote.WebServices
import id.heycoding.storysubmission.data.remote.response.auth.UserLoginResponse
import id.heycoding.storysubmission.databinding.ActivityMainBinding
import id.heycoding.storysubmission.ui.auth.login.LoginFragment
import id.heycoding.storysubmission.ui.home.HomeFragment
import id.heycoding.storysubmission.utils.Preferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var activityMainBinding: ActivityMainBinding? = null
//    lateinit var userLoginPref: Preferences
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding?.root)
//        userLoginPref = Preferences(this)
        supportActionBar?.hide()
        checkSession()
    }

    fun moveToFragment(fragment: Fragment) {
        this.supportFragmentManager.beginTransaction().replace(R.id.mainFragmentContainer, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit()
    }

    private fun checkSession() {
//        if (!userLoginPref.getLoginData().isLogin) {
            moveToFragment(LoginFragment())
//        } else {
//            moveToFragment(HomeFragment())
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
        activityMainBinding = null
    }
}