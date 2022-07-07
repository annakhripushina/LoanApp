package com.example.loan_app.ui.history

import androidx.recyclerview.widget.RecyclerView
import com.example.loan_app.R
import com.example.loan_app.data.model.LoanResponse
import com.example.loan_app.data.model.LoanState
import com.example.loan_app.databinding.LoanItemBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HistoryViewHolder(
    private val binding: LoanItemBinding,
    private val onClick: (Long) -> Unit
) : RecyclerView.ViewHolder(
    binding.root
) {
    fun bind(loan: LoanResponse) {
        val context = binding.root.context
        var textField =
            context.getString(R.string.textAmountValueRub, loan.amount.toString())
        binding.textAmount.text = textField

        val dateFormat = DateTimeFormatter.ofPattern(context.getString(R.string.formatParser))
        textField =
            context.getString(
                R.string.textDateValue, LocalDateTime.parse(loan.date, dateFormat)
                    .format(DateTimeFormatter.ofPattern(context.getString(R.string.formatDateTime)))
            )
        binding.textDate.text = textField

        when (loan.state) {
            LoanState.APPROVED -> {
                binding.textStateValue.text = context.getString(R.string.textApproved)
                binding.imageCard.setImageResource(R.drawable.ic_baseline_card_travel_green)
            }
            LoanState.REGISTERED -> {
                binding.textStateValue.text = context.getString(R.string.textRegistered)
                binding.imageCard.setImageResource(R.drawable.ic_baseline_card_travel_24)
            }
            LoanState.REJECTED -> {
                binding.textStateValue.text = context.getString(R.string.textRejected)
                binding.imageCard.setImageResource(R.drawable.ic_baseline_card_travel_red)
            }
        }

        binding.buttonDetail.setOnClickListener { onClick(loan.id) }
    }
}