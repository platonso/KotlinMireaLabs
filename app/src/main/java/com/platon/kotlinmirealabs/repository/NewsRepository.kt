package com.platon.kotlinmirealabs.repository

import com.platon.kotlinmirealabs.api.RetrofitInstance
import com.platon.kotlinmirealabs.db.ArticleDatabase
import com.platon.kotlinmirealabs.models.Article

class NewsRepository(val db: ArticleDatabase) {

    suspend fun getHeadlines(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getHeadlines(countryCode, pageNumber)

    suspend fun insert(article: Article) = db.articleDao().insert(article)

    fun getFavorites() = db.articleDao().getAllArticles()

}