package com.example.loan_app.screen

import com.example.loan_app.R
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.image.KImageView
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KTextView

object AuthScreen : Screen<AuthScreen>() {

    val login = KEditText { withId(R.id.editTextLogin) }
    val password = KEditText { withId(R.id.editTextPassword) }
    val error = KTextView { withId(R.id.textError) }
    val textRegistration = KTextView { withId(R.id.textRegistration) }
    val textLogin = KTextView { withId(R.id.textLogin) }
    val buttonLogin = KImageView { withId(R.id.buttonLogin) }
    val buttonReg = KImageView { withId(R.id.buttonReg) }

}