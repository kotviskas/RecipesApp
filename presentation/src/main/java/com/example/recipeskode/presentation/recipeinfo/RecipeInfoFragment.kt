package com.example.recipeskode.presentation.recipeinfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeskode.domain.entity.RecipeBrief
import com.example.recipeskode.presentation.R
import com.example.recipeskode.presentation.databinding.FragmentRecipeInfoBinding
import com.example.recipeskode.presentation.recipeinfo.adapters.PhotosSliderAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecipeInfoFragment : Fragment(R.layout.fragment_recipe_info), RecipesBriefAdapter.OnItemClick, PhotosSliderAdapter.OnPhotoClick {

    private var _binding: FragmentRecipeInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<RecipeInfoViewModel>()
    private var adapterSimilarRecipes = RecipesBriefAdapter(ArrayList(), this)
    private var adapterPhoto: PhotosSliderAdapter = PhotosSliderAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val uuid = RecipeInfoFragmentArgs.fromBundle(it).id
            viewModel.getRecipe(uuid)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeInfoBinding.inflate(inflater,container,false)
        viewModel.recipe.observe(viewLifecycleOwner,{
            adapterPhoto.renewItems(it.images)
            adapterSimilarRecipes.update(it.similar)
            binding.apply {
                recipeNameTextView.text = it.name
                descTextView.text = it.description
                tvIntruction.text = it.instructions
                simpleRatingBar.rating = it.difficulty.toFloat()
                imageSlider.setSliderAdapter(adapterPhoto)
                imageSlider.setInfiniteAdapterEnabled(false)
            }
        })
        //ошибка 1 и 2
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //адаптер и стрелочка назад
        binding.similarRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.similarRecyclerView.adapter = adapterSimilarRecipes
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun itemClick(recipe: RecipeBrief) {
        TODO("Not yet implemented")
    }

    override fun itemClick(url: String) {
        TODO("Not yet implemented")
    }

}