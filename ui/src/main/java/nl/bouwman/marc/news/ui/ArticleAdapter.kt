package nl.bouwman.marc.news.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import coil.load
import nl.bouwman.marc.news.ui.databinding.ArticleListItemBinding
import nl.bouwman.marc.news.domain.models.Article

class ArticleAdapter(
    private val articles: LiveData<Set<Article>>,
    private val shouldDisplayLikes: Boolean = true
) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {
    private var lastItemLoadedCallbackInvokedAtPosition: Int? = null

    var onLastItemLoaded: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ArticleListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles.value!!.elementAt(position)

        holder.binding.title.text = article.title

        holder.binding.time.also {
            it.visibility = if(article.humanReadableTimeAndDate == null) View.INVISIBLE else View.VISIBLE
            if(article.humanReadableTimeAndDate != null)
                it.text = article.humanReadableTimeAndDate
        }

        holder.binding.image.load(article.image) {
            placeholder(R.drawable.ic_image)
        }

        holder.binding.root.setOnClickListener {
            val intent = Intent(it.context, ArticleDetailsActivity::class.java)
            intent.putExtra(ArticleDetailsActivity.EXTRA_ARTICLE, article)
            startActivity(it.context, intent, null)
        }

        holder.binding.favorite.visibility = if(article.isLiked && shouldDisplayLikes) View.VISIBLE else View.INVISIBLE

        lastItemCheck(position)
    }

    private fun lastItemCheck(position: Int) {
        if(
            // Don't invoke function on empty lists
            itemCount != 0 &&
            // Only invoke function on last item in list
            position == itemCount - 1 &&
            // Only when there's a function that we actually can invoke
            onLastItemLoaded != null &&
            // Prevent invoking the onLastItemLoaded function multiple times on the same item/position
            position != lastItemLoadedCallbackInvokedAtPosition
        ) {
            lastItemLoadedCallbackInvokedAtPosition = position
            onLastItemLoaded?.invoke()
        }
    }

    override fun getItemCount() = articles.value?.size ?: 0

    class ArticleViewHolder(val binding: ArticleListItemBinding): RecyclerView.ViewHolder(binding.root)
}