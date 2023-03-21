package com.example.news_api.ui.main.News

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.news_api.Api.model.ApiManager
import com.example.news_api.Api.model.NewsResponse.News
import com.example.news_api.Api.model.NewsResponse.NewsResponse
import com.example.news_api.Api.model.SorcesResponse.SourcesItem
import com.example.news_api.ApiConstant
import com.example.news_api.databinding.FragmentNewsBinding
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFragment : Fragment() {
    companion object {
        fun getInstance(source: SourcesItem): NewsFragment {

            val newInstance = NewsFragment()
            newInstance.source = source
            return newInstance

        }

    }

    lateinit var source: SourcesItem
    lateinit var viewBinding: FragmentNewsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentNewsBinding.inflate(inflater, container, false)
        return viewBinding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        getNews()
    }

    val newsAdapter = NewsAdapter(null)
    private fun initRecyclerView() {
        viewBinding.NewsRecycler.adapter = newsAdapter
    }

    private fun getNews() {
        ApiManager
            .getApis()
            .getNews(ApiConstant.apiKey, source.id ?: "")
            .enqueue(object : Callback<NewsResponse> {
                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    showErrorLayout(t.localizedMessage)

                }

                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    if (response.isSuccessful) {
                        bindNewsList(response.body()?.articles)
                        // we have news to show
                        return
                    } else {
                        val errorResponse = Gson().fromJson(
                            response.errorBody()?.string(),
                            NewsResponse::class.java
                        )
                        showErrorLayout(errorResponse.message)

                    }

                }
            })
    }

    private fun bindNewsList(articles: List<News?>?) {
        // show News in recycle view

        newsAdapter.changData(articles)

    }

    private fun showErrorLayout(message: String?) {
        viewBinding.errorLayout.isVisible = true
        viewBinding.loadingIndicator.isVisible = false
        viewBinding.errorMessage.text = message
    }

    private fun showLoadingLayout() {
        viewBinding.loadingIndicator.isVisible = true
        viewBinding.errorLayout.isVisible = false
    }
}