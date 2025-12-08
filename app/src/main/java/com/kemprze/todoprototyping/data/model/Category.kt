package com.kemprze.todoprototyping.data.model

import androidx.annotation.StringRes
import com.kemprze.todoprototyping.R

enum class Category(@StringRes val categoryNameRes: Int,
        @StringRes val categoryImageRes: Int) {
    NONE(categoryNameRes = R.string.category_none, categoryImageRes = R.string.icon_none),
    WORK(categoryNameRes = R.string.category_work, categoryImageRes = R.string.icon_work),
    PERSONAL(categoryNameRes = R.string.category_personal, categoryImageRes = R.string.icon_personal),
    SHOPPING(categoryNameRes = R.string.category_shopping, categoryImageRes = R.string.icon_work),
    HEALTH(categoryNameRes = R.string.category_health, categoryImageRes = R.string.icon_health),
    HOME(categoryNameRes = R.string.category_home, categoryImageRes = R.string.icon_home),
    EDUCATION(categoryNameRes = R.string.category_education, categoryImageRes = R.string.icon_education),
    FINANCE(categoryNameRes = R.string.category_finance, categoryImageRes = R.string.icon_finance),
    OTHER(categoryNameRes = R.string.category_other, categoryImageRes = R.string.icon_other)
}