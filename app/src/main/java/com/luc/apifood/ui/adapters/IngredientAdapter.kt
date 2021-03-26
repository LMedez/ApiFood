package com.luc.apifood.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.luc.apifood.data.model.Ingredient
import com.luc.apifood.databinding.IngredientItemBinding

class IngredientAdapter : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Ingredient>() {
        override fun areItemsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var ingredientList: List<Ingredient>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val binding =
            IngredientItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {

        when (holder) {
            is MainViewHolder -> {
                holder.bind(ingredientList[position], position)
            }
        }
    }

    override fun getItemCount(): Int = ingredientList.size

    private inner class MainViewHolder(
        val binding: IngredientItemBinding
    ) : BaseViewHolder<Ingredient>(binding.root) {
        override fun bind(item: Ingredient, position: Int): Unit = with(binding) {

            ingredientTitle.text = item.name.capitalize()
            Glide.with(this.root)
                .load(item.baseUrl + item.image)
                .into(this.ingredientImage)
        }
    }
}