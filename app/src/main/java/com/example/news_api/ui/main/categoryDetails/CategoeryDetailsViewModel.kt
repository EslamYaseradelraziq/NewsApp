package com.example.news_api.ui.main.categoryDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.news_api.Api.model.ApiManager
import com.example.news_api.Api.model.SorcesResponse.SourcesItem
import com.example.news_api.Api.model.SourcesResponse
import com.example.news_api.ApiConstant
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoeryDetailsViewModel : ViewModel() {
    val sourcesLiveData = MutableLiveData<List<SourcesItem?>?>()
    val showLoadingLayout = MutableLiveData<Boolean>()
    val showErrorLayout = MutableLiveData<String>()
    fun loadNewSources(categoryId: String) {

        showLoadingLayout.value = true
        ApiManager
            .getApis()
            .getSources(apikey = ApiConstant.apiKey, categoryId)
            .enqueue(object : Callback<SourcesResponse> {
                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {

                    if (response.isSuccessful) {
                        sourcesLiveData.value = response.body()?.sources
                    } else {
                        val gson = Gson()
                        val errorResponse =
                            gson.fromJson(
                                response.errorBody()?.string(),
                                SourcesResponse::class.java
                            )
                        showErrorLayout.value = errorResponse.message ?: ""

                    }


                }

                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    showErrorLayout.value = t.localizedMessage

                }
            })

    }

}