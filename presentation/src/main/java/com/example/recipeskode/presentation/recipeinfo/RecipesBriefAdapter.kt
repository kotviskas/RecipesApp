package com.example.recipeskode.presentation.recipeinfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeskode.domain.entity.RecipeBrief
import com.example.recipeskode.presentation.databinding.ItemBreefLayoutBinding
import com.squareup.picasso.Picasso

class RecipesBriefAdapter(
    var listItems: List<RecipeBrief>,
    var listener: OnItemClick
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun update(list: List<RecipeBrief>) {
        listItems = list
        notifyDataSetChanged()
    }


    interface OnItemClick {
        fun itemClick(recipe: RecipeBrief)
    }

    class ItemViewHolder(val binding: ItemBreefLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: RecipeBrief) {
            binding.recipe = recipe
            Picasso.get().load(recipe.images?.get(0)).into(binding.imageBrief)

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemBreefLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

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