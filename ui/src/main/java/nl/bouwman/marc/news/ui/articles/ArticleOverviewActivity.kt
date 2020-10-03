package nl.bouwman.marc.news.ui.articles

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import nl.bouwman.marc.news.ui.R
import nl.bouwman.marc.news.ui.account.AccountOverviewActivity
import nl.bouwman.marc.news.ui.account.AuthenticationActivity
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

        viewModel.isLoggedIn.observe(this) {
            if (viewModel.wasLoggedInOnLastSync != it)
                viewModel.reloadArticles()
        }

        viewModel.isOnline.observe(this) {
            if(!it) Snackbar.make(
                binding.content.recyclerView,
                getString(R.string.no_internet),
                Snackbar.LENGTH_LONG
            ).show()
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