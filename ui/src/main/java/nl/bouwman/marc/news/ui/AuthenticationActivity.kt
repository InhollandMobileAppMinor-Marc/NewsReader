package nl.bouwman.marc.news.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import com.google.android.material.snackbar.Snackbar
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
            viewModel.createAccount(
                binding.content.usernameInputField.text.toString(),
                binding.content.passwordInputField.text.toString()
            )
        }

        binding.content.login.setOnClickListener {
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
            if(it != null) Snackbar.make(binding.content.register, it, Snackbar.LENGTH_LONG).show()
        }
    }
}