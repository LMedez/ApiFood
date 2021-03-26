package com.luc.apifood.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.luc.apifood.core.Resource
import com.luc.apifood.data.model.Recipe
import com.luc.apifood.databinding.FragmentRecipeIngredientsBinding
import com.luc.apifood.presentation.viewmodel.RecipeDetailViewModel
import com.luc.apifood.ui.adapters.IngredientAdapter

private const val ARG_PARAM1 = "param1"


class RecipeIngredientsFragment : Fragment() {
    private var recipe: Recipe? = null

    private val recipeDetailViewModel by activityViewModels<RecipeDetailViewModel>()

    private lateinit var ingredientAdapter: IngredientAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private var _binding: FragmentRecipeIngredientsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recipe = it.getParcelable(ARG_PARAM1)
        }
        ingredientAdapter = IngredientAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeIngredientsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ingredientRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.ingredientRecyclerView.adapter = ingredientAdapter

        recipeDetailViewModel.getRecipeInformation(recipe!!.id)
            .observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Resource.Success -> {
                        Log.d("mesa", result.data.ingredients.toString())
                        ingredientAdapter.ingredientList = result.data.ingredients
                    }
                }
            }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Recipe) =
            RecipeIngredientsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, param1)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}