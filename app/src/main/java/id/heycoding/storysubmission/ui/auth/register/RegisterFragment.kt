package id.heycoding.storysubmission.ui.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import id.heycoding.storysubmission.MainActivity
import id.heycoding.storysubmission.databinding.FragmentRegisterBinding

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var fragmentRegisterBinding: FragmentRegisterBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentRegisterBinding = FragmentRegisterBinding.inflate(inflater, container, false)
        return fragmentRegisterBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.hide()
        initView()
    }

    private fun initView() {
        fragmentRegisterBinding?.apply {
            btnRegister.setOnClickListener {
                validateAndRegister()
            }
        }
    }

    private fun validateAndRegister() {
        when {
            fragmentRegisterBinding?.edtRegisterUsername?.text!!.isBlank() ->
                fragmentRegisterBinding?.edtRegisterUsername!!.error = "Username tidak boleh kosong"
            fragmentRegisterBinding?.edtRegisterEmail?.text!!.isBlank() ->
                fragmentRegisterBinding?.edtRegisterEmail!!.error = "Email tidak boleh kosong"
            fragmentRegisterBinding?.edtRegisterPassword?.text!!.isBlank() ->
                fragmentRegisterBinding?.edtRegisterPassword!!.error = "Password tidak boleh kosong"
            else -> doRegister()
        }
    }

    private fun doRegister() {
        val username = fragmentRegisterBinding?.edtRegisterUsername?.text.toString().trim()
        val userEmail = fragmentRegisterBinding?.edtRegisterEmail?.text.toString().trim()
        val userPassword = fragmentRegisterBinding?.edtRegisterPassword?.text.toString().trim()

        (activity as MainActivity).registerSession(username, userEmail, userPassword)
    }

    override fun onDetach() {
        super.onDetach()
        fragmentRegisterBinding = null
    }
}