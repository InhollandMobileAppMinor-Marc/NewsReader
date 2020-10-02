package nl.bouwman.marc.news.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import nl.bouwman.marc.news.ui.databinding.ActivityAccountOverviewBinding
import nl.bouwman.marc.news.ui.databinding.ActivityArticleOverviewBinding
import nl.bouwman.marc.news.ui.utils.defaultEncryptedPreferences
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountOverviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAccountOverviewBinding

    private lateinit var adapter: ArticleAdapter

    private val viewModel by viewModel<AccountOverviewViewModel>()

    private val preferences by lazy {
        applicationContext.defaultEncryptedPreferences
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAccountOverviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = ArticleAdapter(viewModel.articles, false)
        adapter.onLastItemLoaded = {
            viewModel.loadArticles()
        }

        binding.content.recyclerView.setHasFixedSize(true)
        binding.content.recyclerView.adapter = adapter

        binding.content.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadArticles()
        }

        binding.content.logoutButton.setOnClickListener {
            viewModel.logout()
        }

        binding.content.username.text = preferences.getString(AccountManagerImpl.SETTINGS_USERNAME, null) ?: "N/A"

        viewModel.isLoading.observe(this) {
            binding.content.swipeRefreshLayout.isRefreshing = it
        }

        viewModel.articles.observe(this) {
            adapter.notifyDataSetChanged()
        }

        viewModel.isLoggedIn.observe(this) {
            if(!it) finish()
        }
    }

    override fun onDestroy() {
        adapter.onLastItemLoaded = null
        super.onDestroy()
    }
}