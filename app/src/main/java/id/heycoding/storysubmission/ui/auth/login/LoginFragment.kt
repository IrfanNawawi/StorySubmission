package id.heycoding.storysubmission.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import id.heycoding.storysubmission.MainActivity
import id.heycoding.storysubmission.databinding.FragmentLoginBinding
import id.heycoding.storysubmission.ui.auth.register.RegisterFragment

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var fragmentLoginBinding: FragmentLoginBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)
        return fragmentLoginBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.hide()
        initView()
    }

    private fun initView() {
        fragmentLoginBinding?.apply {
            btnLogin.setOnClickListener {
                validateAndLogin()
            }
            tvRegister.setOnClickListener {
                (activity as MainActivity).moveToFragment(RegisterFragment())
            }
        }
    }

    private fun validateAndLogin() {
        when {
            fragmentLoginBinding?.edtLoginEmail?.text!!.isBlank() ->
                fragmentLoginBinding?.edtLoginEmail?.error = "Email tidak boleh kosong"
            fragmentLoginBinding?.edtLoginPassword?.text!!.isBlank() ->
                fragmentLoginBinding?.edtLoginPassword!!.error = "Password tidak boleh kosong"
            else -> doLogin()
        }
    }

    private fun doLogin() {
        val userEmail = fragmentLoginBinding?.edtLoginEmail?.text.toString().trim()
        val userPassword = fragmentLoginBinding?.edtLoginPassword?.text.toString().trim()

        (activity as MainActivity).loginSession(userEmail, userPassword)
    }

    override fun onDetach() {
        super.onDetach()
        fragmentLoginBinding = null
    }
}