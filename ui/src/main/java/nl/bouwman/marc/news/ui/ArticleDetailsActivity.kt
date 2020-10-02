package nl.bouwman.marc.news.ui

import android.content.Intent
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import coil.load
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import nl.bouwman.marc.news.domain.models.Article
import nl.bouwman.marc.news.ui.databinding.ActivityArticleDetailsBinding
import nl.bouwman.marc.news.ui.utils.CustomTabsHelper
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticleDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticleDetailsBinding

    private val viewModel by viewModel<ArticleDetailsViewModel>()

    private val customTabsSession by lazy {
        CustomTabsHelper.createSession(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityArticleDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.article.observe(this, ::loadArticle)

        binding.content.summary.movementMethod = LinkMovementMethod.getInstance()

        binding.fab.setOnClickListener {
            viewModel.changeLike()
        }

        viewModel.isChangingLike.observe(this) {
            binding.fab.isEnabled = !it
        }

        viewModel.isLoggedIn.observe(this) {
            binding.fab.visibility = if(it) View.VISIBLE else View.GONE
        }

        val article = intent.getSerializableExtra(EXTRA_ARTICLE) as? Article

        if (article != null) {
            viewModel.article.postValue(article)
        } else if (viewModel.article.value != null) {
            MaterialAlertDialogBuilder(this)
                .setMessage(R.string.article_loading_error)
                .setPositiveButton(R.string.ok) { _, _ ->
                    finish()
                }
                .show()
        }

        customTabsSession.observe(this) {
            val articleUrl = viewModel.article.value?.url
            if(articleUrl != null)
                it?.mayLaunchUrl(articleUrl.toUri(), null, null)
        }
    }

    private fun loadArticle(article: Article) {
        binding.toolbarLayout.title = article.title

        binding.content.summary.text =
            if (VERSION.SDK_INT >= VERSION_CODES.N)
                Html.fromHtml(article.summary, Html.FROM_HTML_MODE_COMPACT)
            else
                Html.fromHtml(article.summary)

        binding.actionBarImage.load(article.image)

        binding.content.time.also {
            it.visibility = if(article.humanReadableTimeAndDate == null) View.INVISIBLE else View.VISIBLE
            if(article.humanReadableTimeAndDate != null)
                it.text = article.humanReadableTimeAndDate
        }

        binding.content.source.text = getString(R.string.source_text, article.url)

        val likeDrawable = if(article.isLiked) R.drawable.ic_favorite else R.drawable.ic_favorite_border
        binding.fab.setImageDrawable(ContextCompat.getDrawable(this, likeDrawable))

        customTabsSession.value?.mayLaunchUrl(article.url.toUri(), null, null)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_article_details, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_open_in_browser -> {
                val articleUrl = viewModel.article.value?.url?.toUri() ?: return false

                val customTabsBuilder = CustomTabsIntent.Builder()
                    .setToolbarColor(getColor(R.color.colorPrimary))
                    .addDefaultShareMenuItem()
                    .enableUrlBarHiding()
                    .setShowTitle(true)

                val session = customTabsSession.value
                if(session != null) customTabsBuilder.setSession(session)

                customTabsBuilder.build().launchUrl(this, articleUrl)
            }
            R.id.action_share -> {
                val article = viewModel.article.value ?: return false
                startActivity(Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "\"${article.title}\" - ${article.url}")
                    type = "text/plain"
                })
            }
            else -> return super.onOptionsItemSelected(item)
        }

        return true
    }

    companion object {
        const val EXTRA_ARTICLE = "article"
    }
}