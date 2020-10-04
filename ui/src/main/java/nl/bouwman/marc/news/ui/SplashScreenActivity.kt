package nl.bouwman.marc.news.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import nl.bouwman.marc.news.domain.services.DiApplication
import nl.bouwman.marc.news.ui.articles.ArticleOverviewActivity

class SplashScreenActivity : AppCompatActivity() {
    private val viewModel by viewModels<SplashScreenViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        (application as? DiApplication)?.startDi()

        viewModel.shouldShowSplashScreen.observe(this) {
            if (!it) {
                startActivity(Intent(this, ArticleOverviewActivity::class.java))
                finish()
            }
        }
    }
}
