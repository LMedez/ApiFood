package com.luc.apifood.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.transition.MaterialContainerTransform
import com.luc.apifood.data.model.Recipe
import com.luc.apifood.databinding.FragmentRecipeDetailBinding
import com.luc.apifood.ui.adapters.ViewPagerAdapter


class RecipeDetailFragment : Fragment() {

    private val args: RecipeDetailFragmentArgs by navArgs()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private var _binding: FragmentRecipeDetailBinding? = null
    private val binding get() = _binding!!

    var recipe: Recipe = Recipe()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val materialContainerTransform = MaterialContainerTransform()
        materialContainerTransform.scrimColor = Color.TRANSPARENT
        sharedElementEnterTransition = materialContainerTransform
        sharedElementReturnTransition = materialContainerTransform
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        binding.root.transitionName = args.transitionName
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this)
            .load(args.recipe!!.baseUrl + args.recipe!!.image)
            .into(binding.recipeImage)

        binding.viewPager.adapter = ViewPagerAdapter(parentFragmentManager, lifecycle, args.recipe!!)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position) {
                0 -> tab.text = "Overview"
                1 -> tab.text = "Ingredients"
                2 -> tab.text = "Process"
            }
        }.attach()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}