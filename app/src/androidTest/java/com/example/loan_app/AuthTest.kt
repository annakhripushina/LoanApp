package com.example.loan_app

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.example.loan_app.screen.AuthScreen
import com.example.loan_app.screen.InfoAppScreen
import com.example.loan_app.tools.annotation.TestCase
import com.example.loan_app.ui.MainActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AuthTest : KTestCase() {
    companion object {
        private const val TOKEN_NAME = "TOKEN_NAME"
    }

    private val context: Context = getInstrumentation().targetContext

    private fun setSharedPreferences() {
        context.getSharedPreferences(TOKEN_NAME, Context.MODE_PRIVATE)
            .edit()
            .clear()
            .commit()
    }

    lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        setSharedPreferences()
        val intent = Intent(context, MainActivity::class.java)
        scenario = launchActivity(intent)
    }

    @Test
    @TestCase(name = "Test-1", description = "Check error input login")
    fun checkLogin() {
        run {
            step("Edit password, login null") {
                AuthScreen {
                    password.typeText("123-456Aa")
                    buttonLogin.click()
                    error.hasText(R.string.textErrorLogin)
                }
            }
            step("Edit password, login empty") {
                AuthScreen {
                    login.typeText("")
                    password.clearText()
                    password.typeText("123-456Aa")
                    buttonLogin.click()
                    error.hasText(R.string.textErrorLogin)
                }
            }
            step("Edit password, login not empty or null") {
                AuthScreen {
                    login.typeText("test")
                    password.clearText()
                    password.typeText("123-456Aa")
                    buttonLogin.click()
                    error.hasEmptyText()
                }
            }
        }
    }

    @Test
    @TestCase(name = "Test-2", description = "Check error input password")
    fun checkPassword() {
        run {
            step("Choose registration and enter login") {
                AuthScreen {
                    textRegistration.click()
                    login.typeText("test")
                }
            }
            step("Edit login, password null") {
                AuthScreen {
                    buttonReg.click()
                    error.hasText(R.string.textErrorPassword)
                }
            }
            step("Password empty") {
                AuthScreen {
                    password.typeText("")
                    buttonReg.click()
                    error.hasText(R.string.textErrorPassword)
                }
            }
            step("Password length < 8") {
                AuthScreen {
                    password.clearText()
                    password.typeText("123")
                    buttonReg.click()
                    error.hasText(R.string.textErrorLengthPassword)
                }
            }
            step("Password lower symbols") {
                AuthScreen {
                    password.clearText()
                    password.typeText("123-456aa")
                    buttonReg.click()
                    error.hasText(R.string.textErrorUpperPassword)
                }
            }
            step("Password upper symbols") {
                AuthScreen {
                    password.clearText()
                    password.typeText("123-456AA")
                    buttonReg.click()
                    error.hasText(R.string.textErrorLowerPassword)
                }
            }
            step("Password no special symbols") {
                AuthScreen {
                    password.clearText()
                    password.typeText("123456Aa")
                    buttonReg.click()
                    error.hasText(R.string.textErrorSymbolPassword)
                }
            }
            step("Password is correct") {
                AuthScreen {
                    password.clearText()
                    password.typeText("123-456Aa")
                    buttonReg.click()
                    error.hasEmptyText()
                }
            }
        }
    }

    @Test
    @TestCase(name = "Test-3", description = "Check info app after login")
    fun checkInfoApp() {
        run {
            step("Enter correct login and password") {
                AuthScreen {
                    login.typeText("anna_test")
                    password.typeText("123-456Aa")
                    buttonLogin.click()
                }
            }
            step("Check info app") {
                InfoAppScreen {
                    textHello.isVisible()
                    buttonUnderstand.click()
                }
            }
        }
    }

}