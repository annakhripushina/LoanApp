package com.example.loan_app.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.loan_app.App
import com.example.loan_app.R
import com.example.loan_app.data.repository.RepositoryError
import com.example.loan_app.databinding.AuthFragmentBinding
import com.example.loan_app.extensions.LocaleHelper
import com.example.loan_app.presentation.auth.AuthInputError
import com.example.loan_app.presentation.auth.AuthViewModel
import com.example.loan_app.ui.MainActivity
import com.example.loan_app.ui.history.HistoryFragment
import com.example.loan_app.ui.info.InfoAppFragment
import com.google.android.material.snackbar.Snackbar
import java.util.*
import javax.inject.Inject


class AuthFragment : Fragment() {
    @Inject
    lateinit var viewModel: AuthViewModel

    private lateinit var binding: AuthFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity().applicationContext as App).appComponent.inject(this)
        binding = AuthFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.token.observe(viewLifecycleOwner, Observer {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, HistoryFragment())
                .commit()
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in_up,
                    R.anim.slide_none,
                    R.anim.slide_none,
                    R.anim.slide_out_down
                )
                .replace(R.id.fragmentContainerView, InfoAppFragment())
                .addToBackStack("InfoAppFragment")
                .commit()
        })

        viewModel.regResponse.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                viewModel.loginUser(
                    binding.editTextLogin.text.toString(),
                    binding.editTextPassword.text.toString()
                )
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            showErrorMessage(it)
        })

        binding.buttonLogin.setOnClickListener {
            loginUser()
        }

        binding.textRegistration.setOnClickListener {
            binding.textError.text = ""
            binding.buttonReg.visibility = VISIBLE
            binding.buttonLogin.visibility = INVISIBLE
            binding.textRegistration.setTextAppearance(R.style.baseText18Bold)
            binding.textLogin.setTextAppearance(R.style.baseText18Normal)
        }

        binding.textLogin.setOnClickListener {
            binding.textError.text = ""
            binding.buttonReg.visibility = INVISIBLE
            binding.buttonLogin.visibility = VISIBLE
            binding.textRegistration.setTextAppearance(R.style.baseText18Normal)
            binding.textLogin.setTextAppearance(R.style.baseText18Bold)
        }

        binding.buttonReg.setOnClickListener {
            registrationUser()
        }

        binding.textRu.setOnClickListener {
            changeLocale("ru")

        }
        binding.textEn.setOnClickListener {
            changeLocale("en")

        }

    }

    private fun showErrorMessage(error: RepositoryError) {
        val message = when (error) {
            RepositoryError.ERROR400 -> getString(R.string.textErrorUser)
            RepositoryError.ERROR404 -> getString(R.string.textErrorLoginPassword)
            RepositoryError.ERRORHTTP -> getString(R.string.textHttpException)
            RepositoryError.ERRORNETWORK -> getString(R.string.textException)
            else -> getString(R.string.textErrorUnknown)
        }

        val snackError = Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_LONG
        )
        snackError.show()
    }

    private fun loginUser() {
        when (viewModel.checkLoginData(
            binding.editTextLogin.text.toString(),
            binding.editTextPassword.text.toString()
        )
        ) {
            AuthInputError.ERRORLOGIN -> binding.textError.text = getString(R.string.textErrorLogin)
            AuthInputError.ERRORPASSWORD -> binding.textError.text =
                getString(R.string.textErrorPassword)
            else -> {
                binding.textError.text = null
                viewModel.loginUser(
                    binding.editTextLogin.text.toString(),
                    binding.editTextPassword.text.toString()
                )
            }
        }
    }

    private fun registrationUser() {
        when (viewModel.checkRegData(
            binding.editTextLogin.text.toString(),
            binding.editTextPassword.text.toString()
        )
        ) {
            AuthInputError.ERRORLOGIN -> binding.textError.text = getString(R.string.textErrorLogin)
            AuthInputError.ERRORPASSWORD -> binding.textError.text =
                getString(R.string.textErrorPassword)
            AuthInputError.ERRORLENGTH -> binding.textError.text =
                getString(R.string.textErrorLengthPassword)
            AuthInputError.ERRORUPPER -> binding.textError.text =
                getString(R.string.textErrorUpperPassword)
            AuthInputError.ERRORLOWER -> binding.textError.text =
                getString(R.string.textErrorLowerPassword)
            AuthInputError.ERRORSYMBOL -> binding.textError.text =
                getString(R.string.textErrorSymbolPassword)
            else -> {
                binding.textError.text = null
                viewModel.registrationUser(
                    binding.editTextLogin.text.toString(),
                    binding.editTextPassword.text.toString()
                )
            }
        }
    }

    private fun changeLocale(language: String) {
        LocaleHelper.persist(requireContext(), language)
        MainActivity.locale = Locale(language)
        requireActivity().finish()
        startActivity(requireActivity().intent)
    }

}