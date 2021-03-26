package com.luc.apifood.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.luc.apifood.data.model.Recipe
import com.luc.apifood.ui.fragments.RecipeIngredientsFragment
import com.luc.apifood.ui.fragments.RecipeOverviewFragment

class ViewPagerAdapter (supportFragmentManager: FragmentManager, lifecycle: Lifecycle, val recipe: Recipe = Recipe()) : FragmentStateAdapter(supportFragmentManager, lifecycle)  {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> RecipeOverviewFragment.newInstance(recipe)
            1 -> RecipeIngredientsFragment.newInstance(recipe)
            2 -> RecipeIngredientsFragment.newInstance(recipe)
            else -> Fragment()
        }
    }
}