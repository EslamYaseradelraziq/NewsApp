package com.example.news_api.ui.main.News

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.news_api.Api.model.ApiManager
import com.example.news_api.Api.model.NewsResponse.News
import com.example.news_api.Api.model.NewsResponse.NewsResponse
import com.example.news_api.ApiConstant
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {
    val showLoading = MutableLiveData<Boolean>()
    val showErrorLayout = MutableLiveData<String>()
    val newsList = MutableLiveData<List<News?>?>()

    fun getNews(sourceId: String) {
        showLoading.value = true
        ApiManager
            .getApis()
            .getNews(ApiConstant.apiKey, sourceId)
            .enqueue(object : Callback<NewsResponse> {
                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    showErrorLayout.value = t.localizedMessage

                }

                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    if (response.isSuccessful) {
                        newsList.value = response.body()?.articles
                        // we have news to show
                        return
                    } else {
                        val errorResponse = Gson().fromJson(
                            response.errorBody()?.string(),
                            NewsResponse::class.java
                        )
                        showErrorLayout.value = errorResponse.message ?: ""

                    }

                }
            })
    }
}