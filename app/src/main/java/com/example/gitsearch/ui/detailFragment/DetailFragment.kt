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
import com.example.gitsearch.R
import com.example.gitsearch.data.model.Item
import com.example.gitsearch.databinding.FragmentDetailBinding
import com.example.gitsearch.ui.extensions.viewBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val viewBinding by viewBinding(FragmentDetailBinding::bind)
    private val detailFragmentViewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailFragmentViewModel.onIntent(DetailFragmentIntent.GetInfo)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        observeViewModel()
    }

    private fun setupToolbar() = viewBinding.run {
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
                        viewBinding.progressBar.visibility = View.VISIBLE
                    }
                    is DetailFragmentState.DataLoaded -> {
                        viewBinding.progressBar.visibility = View.GONE
                        getDetailInformation(it.info)
                    }
                    is DetailFragmentState.Error -> {
                        viewBinding.progressBar.visibility = View.GONE
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getDetailInformation(repository: Item?) = viewBinding.run {
        Picasso.get().load(repository?.owner?.avatar_url).into(ivUserAvatarDetailScreen)
        tvUserLogin.text = repository?.owner?.login
        tvNameOfRepository.text = repository?.name
        tvRepositoryDescription.text = repository?.description
        tvProgramingLanguages.text = repository?.language
        tvTopics.text =
            repository?.topics.toString().substring(1, repository?.topics.toString().length - 1)
        tvWatchers.text = "${repository?.watchers_count.toString()} watchers"
    }
}