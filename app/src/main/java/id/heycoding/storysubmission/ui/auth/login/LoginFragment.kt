package id.heycoding.storysubmission.ui.auth.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import id.heycoding.storysubmission.BuildConfig.PREF_NAME
import id.heycoding.storysubmission.MainActivity
import id.heycoding.storysubmission.data.remote.response.auth.AuthSession
import id.heycoding.storysubmission.databinding.FragmentLoginBinding
import id.heycoding.storysubmission.ui.auth.AuthViewModel
import id.heycoding.storysubmission.ui.auth.register.RegisterFragment
import id.heycoding.storysubmission.ui.home.HomeFragment
import id.heycoding.storysubmission.utils.Preferences
import io.reactivex.disposables.CompositeDisposable

class LoginFragment : Fragment() {

    private var loginFragmentBinding: FragmentLoginBinding? = null
    private lateinit var authVM: AuthViewModel
    private lateinit var pref: SharedPreferences
    private lateinit var userLoginPref: Preferences
    private val disposables = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginFragmentBinding = FragmentLoginBinding.inflate(inflater, container, false)
        initVM()
        initPref()
        return loginFragmentBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.hide()
        initView()
    }

    private fun initPref() {
        pref = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        userLoginPref = Preferences(requireContext())
    }

    private fun initView() {
        loginFragmentBinding?.apply {
            btnLogin.setOnClickListener {
                showLoading(true)
                showMessage("Loging in, please wait")
                validateAndLogin()
                showLoading(false)
            }
            tvRegister.setOnClickListener {
                (activity as MainActivity).moveToFragment(RegisterFragment())
            }
        }
    }

    private fun doLogin() {
        val userEmail = loginFragmentBinding?.edtLoginEmail?.text.toString().trim()
        val userPassword = loginFragmentBinding?.edtLoginPassword?.text.toString().trim()

        authVM.apply {
            doLogin(userEmail, userPassword)
            userLogin.observe(viewLifecycleOwner) {
                if (it != null) {
                    //save the login session

                    val currentUser = AuthSession(
                        it.name,
                        it.token,
                        it.userId,
                        true
                    )

                    Log.d("LoginFragment", "doLogin: $currentUser")
                    userLoginPref.setUserLogin(currentUser)

                    Log.i("LoginFragment", "doLogin: $it")
                }

            }
        }

    }

    private fun initVM() {
        authVM = ViewModelProvider(requireActivity())[AuthViewModel::class.java]

        authVM.isLoading.observe(viewLifecycleOwner) { showLoading(it) }
        authVM.message.observe(viewLifecycleOwner) { showMessage(it) }
    }

    private fun validateAndLogin() {
        when {
            loginFragmentBinding?.edtLoginEmail?.text!!.isBlank() -> {
                loginFragmentBinding?.edtLoginEmail?.error = "Username is required"
                return
            }
            loginFragmentBinding?.edtLoginPassword?.text!!.isBlank() -> {
                loginFragmentBinding?.edtLoginPassword!!.error = "Password is required"
                return
            }
        }
        //doLogin
        doLogin()

    }

    private fun showLoading(isLoading: Boolean) {
        loginFragmentBinding?.pgLogin!!.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun onDetach() {
        super.onDetach()
        disposables.dispose()
        loginFragmentBinding = null
    }
}