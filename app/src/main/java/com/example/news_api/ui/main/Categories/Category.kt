package com.example.news_api.ui.main.Categories

import com.example.news_api.R

data class Category(
    var id: String,
    var name: String,
    val imageId: Int,
    val backgroundColorId: Int
) {
    companion object {
        fun getCategoriesList(): List<Category> {


            return listOf(
                Category(
                    id = "sports",
                    name = "sports",
                    backgroundColorId = R.color.red,
                    imageId = R.drawable.ic_sports
                ),
                Category(
                    id = "business",
                    name = "business",
                    backgroundColorId = R.color.orange,
                    imageId = R.drawable.bussines
                ),

                Category(
                    id = " health",
                    name = " health",
                    backgroundColorId = R.color.pink,
                    imageId = R.drawable.health
                ),
                Category(
                    id = " science",
                    name = " science",
                    backgroundColorId = R.color.yellow,
                    imageId = R.drawable.science
                ),
                Category(
                    id = "entertainment",
                    name = "Entertainment",
                    backgroundColorId = R.color.blue,
                    imageId = R.drawable.politics
                ),
                Category(
                    id = "environment",
                    name = "Environment",
                    backgroundColorId = R.color.babyBlue,
                    imageId = R.drawable.environment
                ),


                )
        }
    }
}