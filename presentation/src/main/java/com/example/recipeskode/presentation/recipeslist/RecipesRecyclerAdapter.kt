package com.example.recipeskode.presentation.recipeslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeskode.domain.base.Result
import com.example.recipeskode.domain.entity.Recipe
import com.example.recipeskode.presentation.databinding.RecipeItemLayoutBinding
import com.squareup.picasso.Picasso


class RecipesRecyclerAdapter(
    var listItems: List<Recipe>,
    var listener: OnItemClick
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun update(list: List<Recipe>) {
        listItems = list
        notifyDataSetChanged()
    }


    interface OnItemClick {
        fun itemClick(recipe: Recipe)
    }

    class ItemViewHolder(val binding: RecipeItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: Recipe) {
            binding.recipe = recipe
            if (recipe.images[0] != "") {
                Picasso.get().load(recipe.images[0]).into(binding.imageView)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = RecipeItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ItemViewHolder(binding)

    }

    override fun getItemCount(): Int {
       return listItems.size
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).bind(listItems[position])
        holder.binding.listener = listener
    }
}