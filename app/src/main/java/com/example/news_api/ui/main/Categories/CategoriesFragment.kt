package com.example.news_api.ui.main.Categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.news_api.databinding.FragmentCategoriesBinding

class CategoriesFragment : Fragment() {

    lateinit var viewBinding: FragmentCategoriesBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return viewBinding.root

    }

    val adapter = CategoriesRecyclerAdapter(Category.getCategoriesList())
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.categoriesRecycle.adapter = adapter
        adapter.onItemClickListener = object : CategoriesRecyclerAdapter.OnItemClickListener {
            override fun onItemClick(pos: Int, item: Category) {
                onItemClickListener?.onCategoryClick(item)
            }
        }


    }

    var onItemClickListener: onCategoryClickListener? = null

    interface onCategoryClickListener {
        fun onCategoryClick(category: Category)

    }
}