package nl.bouwman.marc.news.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import nl.bouwman.marc.news.ui.R
import nl.bouwman.marc.news.ui.databinding.ActivityAuthenticationBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthenticationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthenticationBinding

    private val viewModel by viewModel<AuthenticationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.content.register.setOnClickListener {
            hideKeyboard()

            viewModel.createAccount(
                binding.content.usernameInputField.text.toString(),
                binding.content.passwordInputField.text.toString()
            )
        }

        binding.content.login.setOnClickListener {
            hideKeyboard()

            viewModel.login(
                binding.content.usernameInputField.text.toString(),
                binding.content.passwordInputField.text.toString()
            )
        }

        viewModel.isLoading.observe(this) {
            binding.content.register.isEnabled = !it
            binding.content.login.isEnabled = !it
        }

        viewModel.isLoggedIn.observe(this) {
            if(it) {
                startActivity(Intent(this, AccountOverviewActivity::class.java))
                finish()
            }
        }

        viewModel.errorMessage.observe(this) {
            if (it != null) {
                val msg =
                    if(it == AuthenticationViewModel.LOGIN_ERROR_MESSAGE)
                        getString(R.string.incorrect_credentials)
                    else it

                Snackbar.make(binding.content.register, msg, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun hideKeyboard() {
        currentFocus?.clearFocus()

        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val view = currentFocus ?: View(this)
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}