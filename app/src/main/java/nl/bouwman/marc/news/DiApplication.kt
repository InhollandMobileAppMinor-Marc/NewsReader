package nl.bouwman.marc.news

import android.app.Application
import nl.bouwman.marc.news.domain.services.DiApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

@Suppress("unused")
class DiApplicationImpl : Application(), DiApplication {
    override fun startDi() {
        startKoin {
            androidLogger()
            androidContext(this@DiApplicationImpl)
            modules(newsReaderDiModule)
        }
    }

    override fun onTerminate() {
        stopKoin()
        super.onTerminate()
    }
}
