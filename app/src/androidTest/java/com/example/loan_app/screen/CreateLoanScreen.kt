package com.example.loan_app.screen

import com.example.loan_app.R
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView

object CreateLoanScreen : Screen<CreateLoanScreen>() {
    val editTextFirstName = KEditText { withId(R.id.editTextFirstName) }
    val editTextLastName = KEditText { withId(R.id.editTextLastName) }
    val editTextPhone = KEditText { withId(R.id.editTextPhone) }
    val editTextAmount = KEditText { withId(R.id.editTextAmount) }
    val textError = KTextView { withId(R.id.textError) }
    val buttonPost = KButton { withId(R.id.buttonPost) }

}