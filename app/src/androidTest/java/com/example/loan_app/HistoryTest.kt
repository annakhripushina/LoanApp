package com.example.loan_app

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.loan_app.screen.AuthScreen
import com.example.loan_app.screen.HistoryScreen
import com.example.loan_app.screen.InfoAppScreen
import com.example.loan_app.tools.annotation.TestCase
import com.example.loan_app.ui.MainActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HistoryTest : KTestCase() {
    companion object {
        private const val TOKEN_NAME = "TOKEN_NAME"
    }

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

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
    @TestCase(name = "Test-1", description = "Check info button")
    fun checkInfoButton() {
        run {
            step("Enter correct login and password") {
                AuthScreen {
                    login.typeText("anna_test")
                    password.typeText("123-456Aa")
                    buttonLogin.click()
                }
            }
            step("Click info app") {
                InfoAppScreen {
                    buttonUnderstand.click()
                }
            }
            step("Click info button") {
                HistoryScreen {
                    buttonInfo.click()
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

    @Test
    @TestCase(name = "Test-2", description = "Check log out button")
    fun checkLogOutButton() {
        run {
            step("Enter correct login and password") {
                AuthScreen {
                    login.typeText("anna_test")
                    password.typeText("123-456Aa")
                    buttonLogin.click()
                }
            }
            step("Click info app") {
                InfoAppScreen {
                    buttonUnderstand.click()
                }
            }
            step("Click log out button") {
                HistoryScreen {
                    buttonlogOut.click()
                }
            }
            step("Check auth screen") {
                AuthScreen {
                    buttonLogin.isVisible()
                    buttonReg.isInvisible()
                }
            }
        }
    }
}