package com.example.loan_app.ui.createLoan

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.example.loan_app.App
import com.example.loan_app.R
import com.example.loan_app.data.model.LoanResponse
import com.example.loan_app.data.model.LoanState
import com.example.loan_app.data.repository.RepositoryError
import com.example.loan_app.databinding.CreateLoanFragmentBinding
import com.example.loan_app.presentation.createLoan.CreateLoanViewModel
import com.example.loan_app.service.PushService
import com.example.loan_app.ui.info.InfoLoanFragment
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class CreateLoanFragment : Fragment() {
    private lateinit var binding: CreateLoanFragmentBinding
    private lateinit var pushService: PushService

    @Inject
    lateinit var viewModel: CreateLoanViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity().applicationContext as App).appComponent.inject(this)
        binding = CreateLoanFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPushService()
        viewModel.getLoanConditions()

        viewModel.error.observe(viewLifecycleOwner, Observer {
            if (viewModel.conditions.value == null) {
                binding.textSomethingWrong.visibility = VISIBLE
                binding.cardConditions.visibility = INVISIBLE
                binding.cardLoan.visibility = INVISIBLE
                binding.buttonPost.visibility = INVISIBLE
            }
            val error = when (it) {
                RepositoryError.ERRORHTTP -> getString(R.string.textHttpException)
                RepositoryError.ERRORNETWORK -> getString(R.string.textException)
                else -> getString(R.string.textErrorUnknown)
            }
            val snackError = Snackbar.make(
                binding.root,
                error,
                Snackbar.LENGTH_LONG
            )
            snackError.show()
        })

        viewModel.loan.observe(viewLifecycleOwner, Observer {
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in_up,
                    R.anim.slide_none,
                    R.anim.slide_none,
                    R.anim.slide_out_down
                )
                .replace(R.id.fragmentContainerView, InfoLoanFragment())
                .addToBackStack("CompletedLoanFragment")
                .commit()
        })

        viewModel.conditions.observe(viewLifecycleOwner, Observer {
            binding.textPercentValue.text = it.percent.toString()
            binding.textPeriodValue.text = it.period.toString()
            binding.textMaxAmountValue.text = it.maxAmount.toString()
        })

        binding.buttonPost.setOnClickListener {
            when {
                viewModel.checkInput(
                    binding.editTextLastName.text.toString(),
                    binding.editTextFirstName.text.toString(),
                    binding.editTextPhone.text.toString(),
                    binding.editTextAmount.text.toString()
                ) -> {
                    binding.textError.text = getString(R.string.textErrorInput)

                }
                viewModel.checkAmount(
                    binding.textMaxAmountValue.text.toString(),
                    binding.editTextAmount.text.toString()
                ) -> {
                    binding.textError.text = getString(R.string.ErrorAmount)
                }
                else -> viewModel.createLoan(
                    binding.editTextAmount.text.toString().toDouble(),
                    binding.editTextFirstName.text.toString(),
                    binding.editTextLastName.text.toString(),
                    binding.editTextPhone.text.toString()
                )
            }
        }

        binding.buttonClose.setOnClickListener {
            parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        setAmountListeners()

    }

    private fun setAmountListeners() {
        binding.editTextAmount.addTextChangedListener(object : TextWatcher {
            @SuppressLint("SetTextI18n", "ResourceAsColor")
            override fun afterTextChanged(s: Editable) {
                if (binding.editTextAmount.text.isNotEmpty()) {
                    if (viewModel.checkAmount(
                            binding.textMaxAmountValue.text.toString(),
                            binding.editTextAmount.text.toString()
                        )
                    ) {
                        binding.textPayment.text = ""
                        binding.textError.text = getString(R.string.ErrorAmount)
                    } else {
                        binding.textError.text = ""
                        binding.textPayment.text = getString(
                            R.string.textPaymentValue,
                            viewModel.onGetPayment(
                                binding.editTextAmount.text.toString().toDouble(),
                                binding.textPercentValue.text.toString().toDouble(),
                                binding.textPeriodValue.text.toString().toInt()
                            )
                        )
                    }
                } else {
                    binding.textPayment.text = ""
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }
        })
    }

    private fun initPushService() {
        pushService = PushService(requireContext())
    }

}