package com.platon.kotlinmirealabs.repository

import com.platon.kotlinmirealabs.api.NewsApi
import com.platon.kotlinmirealabs.db.ArticleDatabase
import com.platon.kotlinmirealabs.models.Article

class NewsRepository(
    private val api: NewsApi, // Инъекция NewsApi через конструктор
    val db: ArticleDatabase
) {

    // Получаем заголовки новостей через инжектированный API
    suspend fun getHeadlines(countryCode: String, pageNumber: Int) =
        api.getHeadlines(countryCode, pageNumber)

    // Вставляем статью в базу данных
    suspend fun insert(article: Article) = db.articleDao().insert(article)

    // Получаем список избранных статей
    fun getFavorites() = db.articleDao().getAllArticles()
}
