package com.platon.kotlinmirealabs.DI

import androidx.room.Room
import com.platon.kotlinmirealabs.db.ArticleDatabase
import org.koin.dsl.module

val roomModule= module {
    single {
        Room.databaseBuilder(
            get(),
            ArticleDatabase::class.java,
            "article_db.db"
        ).build()
    }

    single { get<ArticleDatabase>().articleDao() }
}
