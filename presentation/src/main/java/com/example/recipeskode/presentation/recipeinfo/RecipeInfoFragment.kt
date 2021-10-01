package com.example.recipeskode.presentation.recipeinfo

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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

    private lateinit var uuid : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            uuid = RecipeInfoFragmentArgs.fromBundle(it).id
            viewModel.getRecipe(uuid)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeInfoBinding.inflate(inflater,container,false)
        setHasOptionsMenu(true)

        viewModel.recipe.observe(viewLifecycleOwner,{
            adapterPhoto.renewItems(it.images)
            adapterSimilarRecipes.update(it.similar)
            binding.apply {
                recipeNameTextView.text = it.name
                tvDate.text = it.lastUpdated
                descTextView.text = it.description
                tvInstruction.text = it.instructions
                simpleRatingBar.rating = it.difficulty.toFloat()
                imageSlider.setSliderAdapter(adapterPhoto)
                imageSlider.setInfiniteAdapterEnabled(false)
            }
        })
        viewModel.internetError.observe(viewLifecycleOwner, {
            showNoInternetError()
        })
        viewModel.apiError.observe(viewLifecycleOwner, {
            showApiError()
        })
        viewModel.noError.observe(viewLifecycleOwner, {
            hideError()
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRefreshInfo.setOnClickListener {
            viewModel.getRecipe(uuid)
        }
        binding.similarRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.similarRecyclerView.adapter = adapterSimilarRecipes
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipe_info_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showNoInternetError() {
        binding.apply {
            tvErrorInfo.text = getString(R.string.no_internet)
            tvMessageInfo.text = getString(R.string.refresh_when_internet)
            clNoInternetInfo.visibility = View.VISIBLE
            linerLayoutRecipe.visibility = View.INVISIBLE
        }
    }

    private fun showApiError() {
        binding.apply {
            tvErrorInfo.text = getString(R.string.api_error)
            tvMessageInfo.text = getString(R.string.refresh_later_api_error)
            clNoInternetInfo.visibility = View.VISIBLE
            linerLayoutRecipe.visibility = View.INVISIBLE
        }
    }

    private fun hideError() {
        binding.apply {
            clNoInternetInfo.visibility = View.INVISIBLE
            linerLayoutRecipe.visibility = View.VISIBLE
        }
    }

    override fun itemClick(url: String) {
        val action = RecipeInfoFragmentDirections.actionRecipeInfoFragmentToRecipePhotoFragment(url)
        findNavController().navigate(action)
    }

    override fun itemClick(recipe: RecipeBrief) {
        val action = RecipeInfoFragmentDirections.actionRecipeInfoFragmentSelf(recipe.uuid)
        findNavController().navigate(action)
    }

}