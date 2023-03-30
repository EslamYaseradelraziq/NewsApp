package com.example.news_api.ui.main.categoryDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.news_api.Api.model.SorcesResponse.SourcesItem
import com.example.news_api.R
import com.example.news_api.databinding.FragmentDetailsCatoegeryBinding
import com.example.news_api.ui.main.Categories.Category
import com.example.news_api.ui.main.News.NewsFragment
import com.google.android.material.tabs.TabLayout

class CatogeryDetailsFragment : Fragment() {
    lateinit var viewBinding: FragmentDetailsCatoegeryBinding
    lateinit var viewModel: CategoeryDetailsViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentDetailsCatoegeryBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // initialize ViewModel
        viewModel = ViewModelProvider(this).get(CategoeryDetailsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadNewSources(category.id)
        subscribeToLiveData()
        changeNewsFragment(SourcesItem())

    }

    fun subscribeToLiveData() {
        // reference on data to view model with liveData
        viewModel.sourcesLiveData.observe(viewLifecycleOwner) {
            bindSourcesInTabLayout(it)
        }
        viewModel.showLoadingLayout.observe(viewLifecycleOwner) { show ->
            if (show) {
                showLoadingLayout()
            } else
                hideLoadingLayout()
        }
        viewModel.showErrorLayout.observe(viewLifecycleOwner) {
            showErrorLayout(it)
        }

    }

    private fun hideLoadingLayout() {
        viewBinding.loadingIndicator.isVisible = false
    }

    fun changeNewsFragment(source: SourcesItem) {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, NewsFragment.getInstance(source))
            .commit()
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
            // custom tabLayout with code
            (tab.view.layoutParams as LinearLayout.LayoutParams).marginStart = 12
            (tab.view.layoutParams as LinearLayout.LayoutParams).marginEnd = 12
//            val layoutPrams = LinearLayout.LayoutParams(tab.view.layoutParams)
//            layoutPrams.marginEnd = 12
//            layoutPrams.marginStart = 12
//            tab.view.layoutParams = layoutPrams

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