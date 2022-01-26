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

    private val viewBinding by viewBinding(FragmentDetailBinding::bind)
    private val detailFragmentViewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    private val catchModel by lazy { args.model }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailFragmentViewModel.onIntent(DetailFragmentIntent.GetDetailInfo(requireNotNull(catchModel)))
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
                        showDetailInformation(it.detailData)
                    }
                    is DetailFragmentState.Error -> {
                        viewBinding.progressBar.visibility = View.GONE
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showDetailInformation(detailData: ItemLocalModel) = viewBinding.run {
        Picasso.get().load(detailData.owner?.avatar_url).into(ivUserAvatarDetailScreen)
        tvUserLogin.text = detailData.owner?.login
        tvNameOfRepository.text = "${detailData.name} repository"
        tvRepositoryDescription.text = detailData.description
        tvProgramingLanguages.text = detailData.language
        tvTopics.text =
            detailData.topics.toString().substring(1, detailData.topics.toString().length - 1)
        tvWatchers.text = "${detailData.watchers.toString()} watchers"
    }
}