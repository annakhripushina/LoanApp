package com.example.loan_app

import android.content.Context
import android.content.Intent
import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.example.loan_app.screen.*
import com.example.loan_app.tools.annotation.TestCase
import com.example.loan_app.ui.MainActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CreateLoanTest : KTestCase() {
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
    @TestCase(name = "Test-1", description = "Check amount when creating a loan")
    fun checkAmount() {
        var maxAmount = 0
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
            step("Click add loan") {
                HistoryScreen {
                    buttonAdd.click()
                }
            }
            step("Enter lastname, name, phone") {
                CreateLoanScreen {
                    editTextLastName.typeText("TestLastName")
                    editTextFirstName.typeText("TestName")
                    editTextPhone.typeText("911")
                }
            }
            step("Enter and check amount > max amount") {
                CreateLoanScreen {
                    closeSoftKeyboard()
                    scenario.onActivity {
                        maxAmount =
                            it.findViewById<TextView>(R.id.textMaxAmountValue).text.toString()
                                .toInt() + 1
                    }
                    editTextAmount.typeText(maxAmount.toString())
                    textError.hasText(R.string.ErrorAmount)
                }
            }
            step("Enter and check amount = max amount") {
                CreateLoanScreen {
                    closeSoftKeyboard()
                    editTextAmount.clearText()
                    scenario.onActivity {
                        maxAmount =
                            it.findViewById<TextView>(R.id.textMaxAmountValue).text.toString()
                                .toInt()
                    }
                    editTextAmount.typeText(maxAmount.toString())
                    textError.hasEmptyText()
                }
            }
        }
    }

    @Test
    @TestCase(name = "Test-2", description = "Check error input field when creating a loan")
    fun checkLoanErrorData() {
        var maxAmount = ""

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
            step("Click add loan") {
                HistoryScreen {
                    buttonAdd.click()
                }
            }
            step("Check empty fields") {
                CreateLoanScreen {
                    buttonPost.click()
                }
            }

            step("Enter lastname, name, phone") {
                CreateLoanScreen {
                    editTextLastName.typeText("TestLastName")
                    editTextFirstName.typeText("TestName")
                    editTextPhone.typeText("911")
                    closeSoftKeyboard()
                    buttonPost.click()
                    textError.hasText(R.string.textErrorInput)
                }
            }
            step("Enter amount, delete phone") {
                CreateLoanScreen {
                    editTextPhone.clearText()
                    closeSoftKeyboard()
                    scenario.onActivity {
                        maxAmount =
                            it.findViewById<TextView>(R.id.textMaxAmountValue).text.toString()
                    }
                    editTextAmount.typeText(maxAmount)
                    closeSoftKeyboard()
                    buttonPost.click()
                    textError.hasText(R.string.textErrorInput)
                }
            }
        }
    }

    @Test
    @TestCase(name = "Test-3", description = "Check create a loan")
    fun checkCreateLoan() {
        var maxAmount = ""

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
            step("Click add loan") {
                HistoryScreen {
                    buttonAdd.click()
                }
            }

            step("Enter loan data") {
                CreateLoanScreen {
                    editTextLastName.typeText("TestLastName")
                    editTextFirstName.typeText("TestName")
                    editTextPhone.typeText("911")
                    closeSoftKeyboard()
                    scenario.onActivity {
                        maxAmount =
                            it.findViewById<TextView>(R.id.textMaxAmountValue).text.toString()
                    }
                    editTextAmount.typeText(maxAmount)
                    closeSoftKeyboard()
                    buttonPost.click()
                }
            }
            step("Check completed info") {
                InfoLoanScreen {
                    textCompleted.isVisible()
                    buttonHistory.click()
                }
            }
        }
    }

}