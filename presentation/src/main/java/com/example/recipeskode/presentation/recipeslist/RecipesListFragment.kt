package com.example.recipeskode.presentation.recipeslist

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeskode.domain.entity.Recipe
import com.example.recipeskode.presentation.R
import com.example.recipeskode.presentation.databinding.FragmentRecipesListBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class RecipesListFragment : Fragment(R.layout.fragment_recipes_list),
    RecipesRecyclerAdapter.OnItemClick, SearchView.OnQueryTextListener {

    private var _binding: FragmentRecipesListBinding? = null
    private val binding get() = _binding!!

    private val viewModel by sharedViewModel<RecipeListViewModel>()
    private var adapterRecipe = RecipesRecyclerAdapter(ArrayList(), this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesListBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        viewModel.recipes.observe(viewLifecycleOwner, { result ->
            adapterRecipe.update(result)
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
        binding.rvRecipes.layoutManager = LinearLayoutManager(context)
        binding.rvRecipes.adapter = adapterRecipe

        binding.btnRefresh.setOnClickListener {
            viewModel.getRecipeList()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipes_menu, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.setOnQueryTextListener(this)
        searchView?.setQuery(viewModel.searchWord.value, false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_settings) {
            val action =
                RecipesListFragmentDirections.actionRecipesListFragmentToBottomSheetSettingsFragment()
            findNavController().navigate(action)
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showNoInternetError() {
        binding.apply {
            tvError.text = getString(R.string.no_internet)
            tvMessage.text = getString(R.string.refresh_when_internet)
            clNoInternet.visibility = View.VISIBLE
            rvRecipes.visibility = View.INVISIBLE
        }
    }

    private fun showApiError() {
        binding.apply {
            tvError.text = getString(R.string.api_error)
            tvMessage.text = getString(R.string.refresh_later_api_error)
            clNoInternet.visibility = View.VISIBLE
            rvRecipes.visibility = View.INVISIBLE
        }
    }

    private fun hideError() {
        binding.apply {
            clNoInternet.visibility = View.INVISIBLE
            rvRecipes.visibility = View.VISIBLE
        }
    }

    override fun itemClick(recipe: Recipe) {
        val action =
            RecipesListFragmentDirections.actionRecipesListFragmentToRecipeInfoFragment(recipe.uuid)
        findNavController().navigate(action)
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        if (p0 != null) {
            viewModel.searchAndSortRecipesList(p0)
        }
        return true
    }
}