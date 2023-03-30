package com.example.news_api.ui.main

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.news_api.R
import com.example.news_api.SettingFragment
import com.example.news_api.databinding.ActivityMainBinding
import com.example.news_api.ui.main.Categories.CategoriesFragment
import com.example.news_api.ui.main.Categories.Category
import com.example.news_api.ui.main.categoryDetails.CatogeryDetailsFragment

class MainActivity : AppCompatActivity(),
    CategoriesFragment.onCategoryClickListener {
    override fun onCategoryClick(category: Category) {
        showCategoryDetailsFragment(category)
    }

    lateinit var viewBinding: ActivityMainBinding
    val categoriesFragment = CategoriesFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        viewBinding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(viewBinding.root)

        val toggle = ActionBarDrawerToggle(
            this, viewBinding.drawer, viewBinding.toolbar,
            R.string.navigation_drower_open,
            R.string.navigation_drower_close
        )
        viewBinding.drawer.addDrawerListener(toggle)
        toggle.syncState()

        viewBinding.navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_Categories -> {
                    showCategoriesFragment()
                }
                R.id.nav_Settings -> {
                    showSettingFragment()
                }


            }
            viewBinding.drawer.closeDrawers()
            return@setNavigationItemSelectedListener true
        }

        categoriesFragment.onItemClickListener = this
        showCategoriesFragment()


    }

    private fun showSettingFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, SettingFragment())
            .commit()
    }

    fun showCategoriesFragment() {
        supportFragmentManager
            .beginTransaction()
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