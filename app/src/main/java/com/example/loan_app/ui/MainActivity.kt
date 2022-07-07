package com.example.loan_app.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.loan_app.App
import com.example.loan_app.R
import com.example.loan_app.extensions.LocaleHelper
import com.example.loan_app.presentation.auth.AuthViewModel
import com.example.loan_app.service.PushReceiver.Companion.NOTIFICATION_PUSH
import com.example.loan_app.ui.auth.AuthFragment
import com.example.loan_app.ui.detailLoan.DetailLoanFragment
import com.example.loan_app.ui.history.HistoryFragment
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: AuthViewModel

    companion object {
        var locale: Locale = Locale("")
    }

    init {
        LocaleHelper.updateConfig(this, locale)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (applicationContext as App).appComponent.inject(this)

        if (viewModel.getToken().isNullOrEmpty()) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, AuthFragment())
                .commit()

        } else
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, HistoryFragment())
                .commit()

        onNewIntent(intent)
    }

    override fun onBackPressed() {
        when {
            supportFragmentManager.backStackEntryCount == 0 -> {
                super.onBackPressed()
            }
            supportFragmentManager.findFragmentByTag("CompletedLoanFragment")?.isVisible != true -> {
                supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handlePushIntent(intent)
        handleLinkIntent(intent)
    }

    private fun showDeepLinkOffer(appLinkAction: String?, appLinkData: Uri?) {
        if (Intent.ACTION_VIEW == appLinkAction && appLinkData != null) {
            val loanId = appLinkData.getQueryParameter("code")
            if (loanId != null) {
                val fragment = loanId.let { DetailLoanFragment.newInstance(it.toLong()) }
                showDetailFragment(fragment)
            }
        }
    }

    private fun handlePushIntent(intent: Intent?) {
        val bundle = intent?.extras
        if (bundle != null) {
            if (bundle.containsKey(NOTIFICATION_PUSH)) {
                val loanId = bundle.getLong(NOTIFICATION_PUSH)
                val fragment = DetailLoanFragment.newInstance(loanId)
                showDetailFragment(fragment)
            }
        }
    }

    private fun handleLinkIntent(intent: Intent?) {
        val appLinkAction: String? = intent?.action
        val appLinkData: Uri? = intent?.data
        showDeepLinkOffer(appLinkAction, appLinkData)
    }

    private fun showDetailFragment(fragment: DetailLoanFragment) {
        if (viewModel.getToken().isNullOrEmpty()) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, AuthFragment())
                .commit()
        } else
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .addToBackStack("DetailLoanFragment")
                .commit()

    }

}