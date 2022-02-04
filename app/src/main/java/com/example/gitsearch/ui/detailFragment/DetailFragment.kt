package com.example.gitsearch.ui.detailFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import com.example.gitsearch.R
import com.example.gitsearch.data.local.model.ItemLocalModel
import com.example.gitsearch.data.local.model.OwnerLocalModel
import com.example.gitsearch.databinding.FragmentDetailBinding
import com.example.gitsearch.ui.extensions.viewBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val viewBinding: FragmentDetailBinding? by viewBinding(FragmentDetailBinding::bind)
    private val detailFragmentViewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    private val modelId by lazy { args.myModelId }
    private val ownerId by lazy { args.myOwnerId }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailFragmentViewModel.onIntent(DetailFragmentIntent.GetAllById(modelId, ownerId))
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        observeViewModel()
    }

    private fun setupToolbar() = viewBinding?.run {
        toolbar.customToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
                detailFragmentViewModel.state.collect {
                    when (it) {
                        is DetailFragmentState.Idle -> {}
                        is DetailFragmentState.Loading -> {
                            viewBinding?.progressBar?.visibility = View.VISIBLE
                        }
                        is DetailFragmentState.DataLoadedAll -> {
                            viewBinding?.progressBar?.visibility = View.GONE
                            showModelInformation(it.model, it.ownerModel)
                        }
                        is DetailFragmentState.Error -> {
                            viewBinding?.progressBar?.visibility = View.GONE
                        }
                    }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showModelInformation(model: ItemLocalModel, ownerModel: OwnerLocalModel) = viewBinding?.run {
        Picasso.get().load(ownerModel.avatar_url).into(ivUserAvatarDetailScreen)
        tvUserLogin.text = ownerModel.login
                tvNameOfRepository.text = "${model.name} repository"
        tvRepositoryDescription.text = model.description
        tvProgramingLanguages.text = model.language
        tvTopics.text =
            model.topics.toString().substring(1, model.topics.toString().length - 1)
        tvWatchers.text = "${model.watchers.toString()} watchers"
    }
}