package com.example.news_api.ui.main.categoryDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.news_api.Api.model.ApiManager
import com.example.news_api.Api.model.SorcesResponse.SourcesItem
import com.example.news_api.Api.model.SourcesResponse
import com.example.news_api.ApiConstant
import com.example.news_api.R
import com.example.news_api.databinding.FragmentDetailsCatoegeryBinding
import com.example.news_api.ui.main.Categories.Category
import com.example.news_api.ui.main.News.NewsFragment
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CatogeryDetailsFragment : Fragment() {
    lateinit var viewBinding: FragmentDetailsCatoegeryBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentDetailsCatoegeryBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadNewSources()
        changeNewsFragment(SourcesItem())

    }

    fun changeNewsFragment(source: SourcesItem) {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, NewsFragment.getInstance(source))
            .commit()
    }

    private fun loadNewSources() {
        showLoadingLayout()
        ApiManager
            .getApis()
            .getSources(apikey = ApiConstant.apiKey, category.id)
            .enqueue(object : Callback<SourcesResponse> {
                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    viewBinding.loadingIndicator.visibility = View.GONE
                    viewBinding.loadingIndicator.isVisible = false
                    if (response.isSuccessful) {
                        bindSourcesInTabLayout(response.body()?.sources)
                    } else {
                        val gson = Gson()
                        val errorResponse =
                            gson.fromJson(
                                response.errorBody()?.string(),
                                SourcesResponse::class.java
                            )
                        showErrorLayout(errorResponse.message)

                    }


                }

                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    showErrorLayout(t.localizedMessage)

                }
            })

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

    fun bindSourcesInTabLayout(sourcesList: List<SourcesItem?>?) {
        sourcesList?.forEach { source ->
            val tab = viewBinding.tabLayout.newTab()
            tab.text = source?.name
            tab.tag = source
            viewBinding.tabLayout.addTab(tab)

        }
        viewBinding.tabLayout
            .addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val source = tab?.tag as SourcesItem
                    changeNewsFragment(source)


                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    val source = tab?.tag as SourcesItem
                    changeNewsFragment(source)


                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }
            })
        viewBinding.tabLayout.getTabAt(0)?.select()


    }

    lateinit var category: Category

    companion object {
        fun getInstance(category: Category): CatogeryDetailsFragment {
            val fragment = CatogeryDetailsFragment()
            fragment.category = category
            return fragment

        }
    }


}