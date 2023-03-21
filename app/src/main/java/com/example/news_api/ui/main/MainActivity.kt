package com.example.news_api.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.news_api.R
import com.example.news_api.databinding.ActivityMainBinding
import com.example.news_api.ui.main.Categories.CategoriesFragment
import com.example.news_api.ui.main.Categories.Category
import com.example.news_api.ui.main.categoryDetails.CatogeryDetailsFragment

class MainActivity : AppCompatActivity(), CategoriesFragment.onCategoryClickListener {
    override fun onCategoryClick(category: Category) {
        showCategoryDetailsFragment(category)
    }

    lateinit var viewBinding: ActivityMainBinding
    val categoriesFragment = CategoriesFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        categoriesFragment.onItemClickListener = this
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, categoriesFragment)
            .commit()

    }

    fun showCategoryDetailsFragment(category: Category) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragment_container,
                CatogeryDetailsFragment.getInstance(category)
            )
            .addToBackStack(null)
            .commit()

    }
}