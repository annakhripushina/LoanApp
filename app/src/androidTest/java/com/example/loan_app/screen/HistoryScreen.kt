package com.example.loan_app.screen

import com.example.loan_app.R
import io.github.kakaocup.kakao.image.KImageView
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton

object HistoryScreen : Screen<HistoryScreen>() {
    val buttonAdd = KImageView { withId(R.id.buttonAdd) }
    val buttonInfo = KButton { withId(R.id.buttonInfo) }
    val buttonlogOut = KButton { withId(R.id.buttonLogOut) }
}