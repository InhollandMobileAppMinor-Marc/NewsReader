package nl.bouwman.marc.news.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import nl.bouwman.marc.news.ui.databinding.ActivityArticleOverviewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticleOverviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticleOverviewBinding

    private lateinit var adapter: ArticleAdapter

    private val viewModel by viewModel<ArticleOverviewViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityArticleOverviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        adapter = ArticleAdapter(viewModel.articles)
        adapter.onLastItemLoaded = {
            viewModel.loadArticles()
        }

        binding.content.recyclerView.setHasFixedSize(true)
        binding.content.recyclerView.adapter = adapter

        binding.content.swipeRefreshLayout.setOnRefreshListener {
            viewModel.reloadArticles()
        }

        viewModel.isLoading.observe(this) {
            binding.content.swipeRefreshLayout.isRefreshing = it
        }

        viewModel.articles.observe(this) {
            adapter.notifyDataSetChanged()
        }
    }

    override fun onDestroy() {
        adapter.onLastItemLoaded = null
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_article_overview, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_account_info -> {
                val destination =
                    if(viewModel.isLoggedIn.value == true) AccountOverviewActivity::class
                    else AuthenticationActivity::class

                startActivity(Intent(this, destination.java))
            }
            else -> return super.onOptionsItemSelected(item)
        }

        return true
    }
}