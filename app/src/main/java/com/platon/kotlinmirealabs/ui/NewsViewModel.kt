package com.platon.kotlinmirealabs.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.platon.kotlinmirealabs.models.Article
import com.platon.kotlinmirealabs.models.NewsResponse
import com.platon.kotlinmirealabs.repository.NewsRepository
import com.platon.kotlinmirealabs.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import java.util.Locale.IsoCountryCode

class NewsViewModel(app: Application, var newsRepository: NewsRepository): AndroidViewModel(app) {

    val headlines: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var headlinesPage = 1
    var headlinesResponse: NewsResponse? = null

    init {
        getHeadlines("us")
    }

    fun getHeadlines(countryCode: String) = viewModelScope.launch {
        headlinesInternet(countryCode)
    }

    private fun handleHeadlinesResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                headlinesPage++
                if (headlinesResponse == null) {
                    headlinesResponse = resultResponse
                } else {
                    val oldArticles = headlinesResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(headlinesResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun addToFavorites(article: Article) = viewModelScope.launch {
        newsRepository.insert(article)
    }

    fun getFavoriteNews() = newsRepository.getFavorites()

    fun internetConnection(context: Context): Boolean? {
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).apply {
            return getNetworkCapabilities(activeNetwork)?.run {
                when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            } ?: false
        }
    }

    private suspend fun headlinesInternet(countryCode: String) {
        headlines.postValue(Resource.Loading())
        try {
            if (internetConnection(this.getApplication()) == true) {
                val response = newsRepository.getHeadlines(countryCode, headlinesPage)
                headlines.postValue(handleHeadlinesResponse(response))

            }
        }catch (t: Throwable){
            when (t) {
                is IOException -> headlines.postValue(Resource.Error("${t.message}"))
                else -> headlines.postValue(Resource.Error("No signal"))
            }
        }
    }
}
