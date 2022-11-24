package id.heycoding.storysubmission

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import dagger.hilt.android.AndroidEntryPoint
import id.heycoding.storysubmission.data.remote.response.auth.AuthSession
import id.heycoding.storysubmission.databinding.ActivityMainBinding
import id.heycoding.storysubmission.ui.auth.AuthViewModel
import id.heycoding.storysubmission.ui.auth.login.LoginFragment
import id.heycoding.storysubmission.ui.home.HomeFragment
import id.heycoding.storysubmission.viewmodel.DataStoreViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var activityMainBinding: ActivityMainBinding? = null
    private val authViewModel by viewModels<AuthViewModel>()
    private val dataStoreViewModel by viewModels<DataStoreViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding?.root)
        supportActionBar?.hide()

        checkSession()
        initViewModel()
    }

    private fun initViewModel() {
        authViewModel.isLoading.observe(this) { showLoading(it) }
        authViewModel.message.observe(this) { showMessage(it) }
    }

    fun moveToFragment(fragment: Fragment) {
        this.supportFragmentManager.beginTransaction().replace(R.id.mainFragmentContainer, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logoutMenu -> {
                doLogout()
                true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    private fun doLogout() {
        dataStoreViewModel.logout()
        moveToFragment(LoginFragment())
    }

    private fun checkSession() {
        dataStoreViewModel.getAuthSession().observe(this) {
            if (!it.isLogin) {
                moveToFragment(LoginFragment())
            } else {
                moveToFragment(HomeFragment())
            }
        }
    }

    fun loginSession(email: String, pass: String) {
        authViewModel.apply {
            showLoading(true)
            doLogin(email, pass)
            userLogin.observe(this@MainActivity) {
                if (it != null) {
                    val currentUser = AuthSession(
                        it.userId,
                        it.name,
                        it.token,
                        true
                    )

                    dataStoreViewModel.setAuthSession(currentUser)
                }
                showLoading(false)

            }
        }
    }

    fun registerSession(username: String, email: String, password: String) {
        authViewModel.apply {
            showLoading(true)
            doRegister(username, email, password)
            isError.observe(this@MainActivity) {
                if (it != true) {
                    moveToFragment(LoginFragment())
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        activityMainBinding?.pgMain?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showLoadingShimmer(isLoading: Boolean) {
        activityMainBinding?.pgMainShimmer?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        activityMainBinding = null
    }
}