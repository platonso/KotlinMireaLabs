package com.platon.kotlinmirealabs

import com.platon.kotlinmirealabs.repository.NewsRepository
import com.platon.kotlinmirealabs.ui.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { NewsRepository(get(), get()) }
    viewModel { NewsViewModel(get(), get()) }

}