package com.example.news_api.ui.main.News

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.news_api.Api.model.NewsResponse.News
import com.example.news_api.Api.model.SorcesResponse.SourcesItem
import com.example.news_api.databinding.FragmentNewsBinding

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
    lateinit var viewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
    }

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
        viewModel.getNews(source.id ?: "")
        subscribeToLiveData()
    }

    fun subscribeToLiveData() {
        viewModel.newsList.observe(viewLifecycleOwner) {
            bindNewsList(it)
        }
        viewModel.showErrorLayout.observe(viewLifecycleOwner) {
            showErrorLayout(it)
        }
        viewModel.showLoading.observe(viewLifecycleOwner) {
            if (it)
                showLoadingLayout()
            else
                hideLoading()
        }
    }

    private fun hideLoading() {
        viewBinding.loadingIndicator.isVisible = false
    }

    val newsAdapter = NewsAdapter(null)
    fun initRecyclerView() {
        viewBinding.NewsRecycler.adapter = newsAdapter
    }


    fun bindNewsList(articles: List<News?>?) {
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