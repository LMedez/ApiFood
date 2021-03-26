package com.luc.apifood.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.luc.apifood.data.model.Recipe
import com.luc.apifood.databinding.RecipeCardBinding
import java.util.*

class RecipeAdapter : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var onItemClickListener: ((Recipe, Int, View) -> Unit)? = null

    fun setItemClickListener(listener: (Recipe, Int, View) -> Unit) {
        onItemClickListener = listener
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var recipeList: List<Recipe>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val binding =
            RecipeCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = MainViewHolder(binding)

        binding.root.setOnClickListener {
            val position =
                holder.adapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                    ?: return@setOnClickListener
            onItemClickListener?.let { click ->
                click(recipeList[position], position, binding.imageContainerCard)
            }
        }

        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {

        when (holder) {
            is MainViewHolder -> {
                holder.bind(recipeList[position], position)
                holder.binding.imageContainerCard.transitionName = recipeList[position].title+ position
            }
        }
    }

    override fun getItemCount(): Int = recipeList.size

    private inner class MainViewHolder(
        val binding: RecipeCardBinding
    ) : BaseViewHolder<Recipe>(binding.root) {
        override fun bind(item: Recipe, position: Int): Unit = with(binding) {

            if (item.title.indexOf("{") != -1) {
                item.title = item.title.removeRange(item.title.indexOf("{"), item.title.length)
            }

            recipeTitle.text = item.title.capitalize(Locale.ROOT)
            recipeCookingTime.text = item.readyInMinutes.toString() +" min"
            recipeServings.text = item.servings + " Servings"
            Glide.with(this.root)
                .load(item.baseUrl + item.image)
                .into(this.recipeImage)
        }
    }
}