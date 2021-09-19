package com.example.recipeskode.presentation.recipeslist.bottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.recipeskode.presentation.R
import com.example.recipeskode.presentation.databinding.FragmentBottomSheetSettingsBinding
import com.example.recipeskode.presentation.recipeslist.RecipeListViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BottomSheetSettingsFragment : BottomSheetDialogFragment() {

    private var _binding : FragmentBottomSheetSettingsBinding?= null
    private val binding get() = _binding!!

    private val viewModel by sharedViewModel<RecipeListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetSettingsBinding.inflate(inflater,container,false)

        binding.tvSortByName.setOnClickListener {
            viewModel.setOptionByName()
            viewModel.sortRecipe()
            val action = BottomSheetSettingsFragmentDirections.actionBottomSheetSettingsFragmentToRecipesListFragment()
            findNavController().navigate(action)
        }

        binding.tvSortByDate.setOnClickListener {
            viewModel.setOptionByDate()
            viewModel.sortRecipe()
            val action = BottomSheetSettingsFragmentDirections.actionBottomSheetSettingsFragmentToRecipesListFragment()
            findNavController().navigate(action)

        }

        return binding.root
    }

}