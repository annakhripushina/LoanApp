package com.example.loan_app.ui.history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loan_app.data.model.LoanResponse
import com.example.loan_app.databinding.LoanItemBinding

class HistoryAdapter(private val onClick: (Long) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var loanList: List<LoanResponse> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = LoanItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding, onClick)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HistoryViewHolder -> {
                holder.bind(loanList[position])
            }
        }
    }

    override fun getItemCount(): Int =
        loanList.size
}