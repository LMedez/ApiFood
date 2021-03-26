package com.luc.apifood.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.transition.Hold
import com.luc.apifood.core.Resource
import com.luc.apifood.data.model.Recipe
import com.luc.apifood.databinding.FragmentHomeBinding
import com.luc.apifood.presentation.viewmodel.HomeViewModel
import com.luc.apifood.ui.adapters.RecipeAdapter
import com.luc.apifood.ui.utils.hide
import com.luc.apifood.ui.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel by activityViewModels<HomeViewModel>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var recipeList: List<Recipe>

    private lateinit var recipeAdapter: RecipeAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipeAdapter = RecipeAdapter()
        exitTransition = Hold()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        view.doOnPreDraw {
            startPostponedEnterTransition()
        }

        binding.recipesRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recipesRecycler.adapter = recipeAdapter

//        binding.viewPager.adapter = recipeAdapter
//        binding.viewPager.offscreenPageLimit = 3
//        binding.viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
//        val compositePageTransformer = CompositePageTransformer()
//        compositePageTransformer.addTransformer(MarginPageTransformer(40))
//        compositePageTransformer.addTransformer { page, position ->
//            val r = 1 - abs(position)
//            page.scaleY = 0.85f + r * 0.15f
//        }
//        binding.viewPager.setPageTransformer(compositePageTransformer)

        binding.tabLayout.addOnTabSelectedListener(onTabSelectedListener)

        recipeAdapter.setItemClickListener { recipe, _, sharedView ->
            val extras = FragmentNavigatorExtras(
                sharedView to sharedView.transitionName,
            )

            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToRecipeDetailFragment(
                    sharedView.transitionName,
                    recipe
                ), extras
            )
        }

        getRecipesByType("breakfast")
    }

    private fun getRecipesByType(type: String) {
        homeViewModel.getRecipesByType(type).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.show()
                }

                is Resource.Success -> {
                    recipeAdapter.recipeList = result.data
                    recipeList = result.data
                    binding.progressBar.hide()
                }

                is Resource.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "ERROR ${result.exception}",
                        Toast.LENGTH_LONG
                    ).show()
                    binding.progressBar.hide()
                }
            }
        }
    }

    private val onTabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            getRecipesByType(tab?.text.toString().toLowerCase())
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {}

        override fun onTabReselected(tab: TabLayout.Tab?) {}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}