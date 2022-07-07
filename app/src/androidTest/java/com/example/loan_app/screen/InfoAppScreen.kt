package com.example.loan_app.screen

import com.example.loan_app.R
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView

object InfoAppScreen : Screen<InfoAppScreen>() {
    val textHello = KTextView { withId(R.id.textHello) }
    val buttonUnderstand = KButton { withId(R.id.buttonUnderstand) }
}