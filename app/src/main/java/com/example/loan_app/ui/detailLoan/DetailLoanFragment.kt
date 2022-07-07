package com.example.loan_app.ui.detailLoan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.loan_app.App
import com.example.loan_app.R
import com.example.loan_app.data.model.LoanResponse
import com.example.loan_app.data.model.LoanState
import com.example.loan_app.data.repository.RepositoryError
import com.example.loan_app.databinding.DetailLoanFragmentBinding
import com.example.loan_app.presentation.detailLoan.DetailLoanViewModel
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class DetailLoanFragment : Fragment() {

    companion object {
        fun newInstance(id: Long): DetailLoanFragment =
            DetailLoanFragment().apply {
                arguments = bundleOf(LOAN_ID to id)
            }

        private const val LOAN_ID = "LOAN_ID"
    }

    private lateinit var binding: DetailLoanFragmentBinding

    @Inject
    lateinit var viewModel: DetailLoanViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity().applicationContext as App).appComponent.inject(this)
        binding = DetailLoanFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = getLoanId() ?: return

        viewModel.getLoan(id)

        viewModel.loan.observe(viewLifecycleOwner, Observer { loan ->
            setFields(loan)
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            val error = when (it) {
                RepositoryError.ERRORHTTPDB -> getString(R.string.textHttpExceptionDb)
                RepositoryError.ERRORNETWORKDB -> getString(R.string.textExceptionDb)
                else -> getString(R.string.textErrorUnknown)
            }
            val snackError = Snackbar.make(
                binding.root,
                error,
                Snackbar.LENGTH_LONG
            )
            snackError.show()
        })

        binding.buttonClose.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

    }

    private fun setFields(loan: LoanResponse) {
        binding.textAmountValue.text = loan.amount.toString()
        binding.textPercentValue.text = loan.percent.toString()
        binding.textPeriodValue.text = loan.period.toString()
        binding.textFisrtNameValue.text = loan.firstName
        binding.textLastNameValue.text = loan.lastName
        binding.textPhoneValue.text = loan.phoneNumber


        val dateFormat = DateTimeFormatter.ofPattern(getString(R.string.formatParser))
        val textField = LocalDateTime.parse(loan.date, dateFormat)
            .format(DateTimeFormatter.ofPattern(getString(R.string.formatDateTime)))
        binding.textDateValue.text = textField

        binding.textRepaymentValue.text = LocalDateTime.parse(loan.date, dateFormat)
            .plusDays(loan.period.toLong())
            .format(DateTimeFormatter.ofPattern(getString(R.string.formatDate)))

        when (loan.state) {
            LoanState.APPROVED -> {
                binding.textStateValue.text = getString(R.string.textApproved)
                binding.cardGetLoanInfo.visibility = VISIBLE
                binding.textRepayment.visibility = VISIBLE
                binding.textRepaymentValue.visibility = VISIBLE
            }
            LoanState.REGISTERED -> {
                binding.textStateValue.text = getString(R.string.textRegistered)
            }
            LoanState.REJECTED -> {
                binding.textStateValue.text = getString(R.string.textRejected)
            }
        }
    }

    private fun getLoanId(): Long? =
        arguments?.getLong(LOAN_ID)
}