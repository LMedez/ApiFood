package com.luc.apifood.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.chip.Chip
import com.luc.apifood.core.Resource
import com.luc.apifood.data.model.Nutrients
import com.luc.apifood.data.model.Recipe
import com.luc.apifood.databinding.FragmentRecipeOverviewBinding
import com.luc.apifood.presentation.viewmodel.RecipeDetailViewModel
import com.luc.apifood.ui.utils.hide
import com.luc.apifood.ui.utils.show
import org.jsoup.Jsoup

private const val ARG_PARAM1 = "param1"

class RecipeOverviewFragment : Fragment() {
    private var recipe: Recipe? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private var _binding: FragmentRecipeOverviewBinding? = null
    private val binding get() = _binding!!

    private val recipeDetailViewModel by activityViewModels<RecipeDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recipe = it.getParcelable(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipeDetailViewModel.getRecipeInformation(recipe!!.id)
            .observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Resource.Loading -> {
                        binding.progress.show()
                        binding.root.hide()
                    }
                    is Resource.Success -> {
                        binding.progress.hide()
                        binding.root.show()
                        binding.recipeTitle.text = recipe!!.title
                        binding.recipeCaloriesValue.text =
                            nutritionConverter("Calories", result.data.nutrition.nutrients)
                        binding.recipeProteinValue.text =
                            nutritionConverter("Protein", result.data.nutrition.nutrients)
                        binding.recipeFatValue.text =
                            nutritionConverter("Fat", result.data.nutrition.nutrients)
                        binding.recipeSummary.text = Jsoup.parse(result.data.summary).text()
                        result.data.diets.forEach {
                            val chip = Chip(requireContext())
                            chip.text = it.replace("\\s".toRegex(), "-")
                            binding.chipContainer.addView(chip)
                        }
                    }
                    is Resource.Error -> binding.progress.hide()
                }
            }
    }

    private fun nutritionConverter(nutrientType: String, nutrientList: List<Nutrients>) =
        nutrientList.find { it.name == nutrientType }!!.amount.toInt().toString() + "g"


    companion object {
        @JvmStatic
        fun newInstance(recipe: Recipe) =
            RecipeOverviewFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, recipe)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}