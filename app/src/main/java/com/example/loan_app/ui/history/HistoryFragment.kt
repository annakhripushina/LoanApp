package com.example.loan_app.ui.history

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
import com.example.loan_app.data.model.LoanResponse
import com.example.loan_app.data.model.LoanState
import com.example.loan_app.data.repository.RepositoryError
import com.example.loan_app.databinding.HistoryFragmentBinding
import com.example.loan_app.presentation.history.HistoryViewModel
import com.example.loan_app.service.PushService
import com.example.loan_app.ui.auth.AuthFragment
import com.example.loan_app.ui.createLoan.CreateLoanFragment
import com.example.loan_app.ui.detailLoan.DetailLoanFragment
import com.example.loan_app.ui.info.InfoAppFragment
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject


class HistoryFragment : Fragment() {
    private lateinit var binding: HistoryFragmentBinding
    private val historyAdapter = HistoryAdapter(onClick = {
        val fragment = DetailLoanFragment.newInstance(it)

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .addToBackStack("DetailLoanFragment")
            .commit()
    })

    @Inject
    lateinit var viewModel: HistoryViewModel
    private lateinit var pushService: PushService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity().applicationContext as App).appComponent.inject(this)
        binding = HistoryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAlarmService()
        viewModel.getLoanList()

        viewModel.loanList.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                binding.textEmptyList.visibility = VISIBLE
            } else {
                historyAdapter.loanList = it
                binding.textEmptyList.visibility = INVISIBLE
                it.forEach { loan ->
                    if (loan.state == LoanState.APPROVED) {
                        val pushTime = getPushTime(loan)
                        pushService.setExactAlarm(
                            loan.id,
                            pushTime,
                            loan.id.toInt()
                        )
                    }
                }
            }
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

        binding.recyclerView.apply {
            adapter = historyAdapter
        }

        binding.buttonAdd.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, CreateLoanFragment())
                .addToBackStack("CreateLoanFragment")
                .commit()

        }

        binding.swipeContainer.setOnRefreshListener {
            viewModel.getLoanList()
            binding.swipeContainer.isRefreshing = false
        }

        binding.buttonInfo.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, InfoAppFragment())
                .addToBackStack("InfoAppFragment")
                .commit()
        }

        binding.buttonLogOut.setOnClickListener {
            viewModel.logOut()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, AuthFragment())
                .commit()
        }

    }

    private fun initAlarmService() {
        pushService = PushService(requireContext())
    }

    private fun getPushTime(loan: LoanResponse): Long =
        LocalDateTime
            .parse(
                loan.date,
                DateTimeFormatter.ofPattern(requireContext().getString(R.string.formatParser))
            )
            .plusDays(loan.period - 3L)
            .atOffset(ZoneOffset.UTC)
            .toInstant()
            .toEpochMilli()


}