package com.example.recipeskode.presentation.photo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import com.example.recipeskode.presentation.R
import com.example.recipeskode.presentation.databinding.FragmentRecipePhotoBinding
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel


class RecipePhotoFragment : Fragment(R.layout.fragment_recipe_photo) {

    private var _binding: FragmentRecipePhotoBinding? = null
    private val binding get() = _binding!!
    var url = ""
    private val viewModel by viewModel<RecipePhotoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = RecipePhotoFragmentArgs.fromBundle(it).url
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipePhotoBinding.inflate(inflater, container, false)
        binding.btnSavePhoto.setOnClickListener {
            viewModel.saveImage(binding.imageSavePhoto.drawable.toBitmap(), requireActivity())
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Picasso.get().load(url).into(binding.imageSavePhoto)
    }
}