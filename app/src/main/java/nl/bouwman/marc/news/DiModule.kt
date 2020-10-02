package nl.bouwman.marc.news

import nl.bouwman.marc.news.api.NewsReaderApiImpl
import nl.bouwman.marc.news.domain.services.NewsReaderApi
import nl.bouwman.marc.news.ui.ArticleDetailsViewModel
import nl.bouwman.marc.news.ui.ArticleOverviewViewModel
import nl.bouwman.marc.news.ui.account.AccountManager
import nl.bouwman.marc.news.ui.account.AccountManagerImpl
import nl.bouwman.marc.news.ui.account.AccountOverviewViewModel
import nl.bouwman.marc.news.ui.account.AuthenticationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val newsReaderDiModule = module {
    single<NewsReaderApi> {
        NewsReaderApiImpl()
    }

    single<AccountManager> {
        AccountManagerImpl(get(), get())
    }

    viewModel {
        ArticleOverviewViewModel(get(), get())
    }

    viewModel {
        ArticleDetailsViewModel(get(), get())
    }

    viewModel {
        AccountOverviewViewModel(get(), get())
    }

    viewModel {
        AuthenticationViewModel(get())
    }
}
