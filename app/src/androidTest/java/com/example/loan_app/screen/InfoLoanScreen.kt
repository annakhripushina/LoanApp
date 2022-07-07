package com.example.loan_app.screen

import com.example.loan_app.R
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView

object InfoLoanScreen : Screen<InfoLoanScreen>() {
    val textCompleted = KTextView { withId(R.id.textCompleted) }
    val buttonHistory = KButton { withId(R.id.buttonHistory) }
}