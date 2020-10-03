package nl.bouwman.marc.news.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import nl.bouwman.marc.news.ui.articles.ArticleOverviewActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashScreenActivity : AppCompatActivity() {
    private val viewModel by viewModel<SplashScreenViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
    }

    override fun onStart() {
        super.onStart()

        viewModel.shouldShowSplashScreen.observe(this) {
            if (!it) {
                startActivity(Intent(this, ArticleOverviewActivity::class.java))
                finish()
            }
        }
    }
}