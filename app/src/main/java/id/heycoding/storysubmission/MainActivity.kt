package id.heycoding.storysubmission

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import id.heycoding.storysubmission.databinding.ActivityMainBinding
import id.heycoding.storysubmission.ui.auth.login.LoginFragment
import id.heycoding.storysubmission.ui.auth.register.RegisterFragment
import id.heycoding.storysubmission.utils.UserLoginPreferences
import io.reactivex.disposables.CompositeDisposable

class MainActivity : AppCompatActivity() {

    private var activityMainBinding: ActivityMainBinding? = null
    lateinit var userLoginPref: UserLoginPreferences
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding?.root)
        supportActionBar?.hide()
        checkSession()
    }

    fun moveToFragment(fragment: Fragment) {
        this.supportFragmentManager.beginTransaction().replace(R.id.mainFragmentContainer, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit()
    }

    private fun checkSession() {
//        if (!userLoginPref.getLoginData().isLogin) {
        moveToFragment(RegisterFragment())
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