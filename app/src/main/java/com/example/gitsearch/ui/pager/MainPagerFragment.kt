package com.example.gitsearch.ui.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.compose.foundation.background
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import com.example.gitsearch.R
import com.example.gitsearch.databinding.FragmentMainPagerBinding
import com.example.gitsearch.ui.compose.theme.AppTheme
import com.example.gitsearch.ui.extensions.viewBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.system.exitProcess

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalCoroutinesApi
class MainPagerFragment : Fragment() {

    /* private val viewBinding: FragmentMainPagerBinding? by viewBinding(
         FragmentMainPagerBinding::bind)*/
    private val viewModel: SearchViewModel by viewModels()

    // private lateinit var mainTabLayout: TabLayout
    // private val pagerSharedViewModel: PagerSharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // val toolbar = viewBinding?.tbPagerFragment
        // toolbar?.setNavigationOnClickListener { exitProcess(0) }
        //setupUI()
        // setupPager()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    Surface(color = MaterialTheme.colors.background) {
                        SearchViewActionBar(viewModel = viewModel)
                    }
                }
            }
        }
    }

    @Composable
    fun SearchViewActionBar(viewModel: SearchViewModel) {
        val searchState by viewModel.searchState
        val searchTextState by viewModel.searchTextState
        val context = LocalContext.current

        Scaffold(
            topBar = {
                AppBarStates(
                    searchState = searchState,
                    searchTextState = searchTextState,
                    onTextChange = {
                        viewModel.updateSearchTextState(it)
                    },
                    onCloseClicked = {
                        viewModel.updateSearchState(SearchState.CLOSED)
                    },
                    onSearchClicked = {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    },
                    onSearchTriggered = {
                        viewModel.updateSearchState(SearchState.OPENED)
                    }
                )
            },
            content = {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Image",
                        tint = Color.Gray,
                        modifier = Modifier.size(90.dp)
                    )
                    Text(
                        text = "Find something on GitHub!",
                        fontSize = 25.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Gray
                    )
                }
            }
        )
    }


    @Composable
    private fun SearchAppBar(
        text: String,
        onTextChange: (String) -> Unit,
        onCloseClicked: () -> Unit,
        onSearchClicked: (String) -> Unit
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            elevation = AppBarDefaults.TopAppBarElevation,
            color = MaterialTheme.colors.primary
        ) {
            TextField(
                value = text,
                onValueChange = { onTextChange(it) },
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Search...",
                        color = Color.White,
                        modifier = Modifier.alpha(ContentAlpha.medium)
                    )
                },
                textStyle = TextStyle(
                    fontSize = MaterialTheme.typography.subtitle1.fontSize
                ),
                singleLine = true,
                leadingIcon = {
                    IconButton(
                        onClick = { },
                        modifier = Modifier.alpha(ContentAlpha.medium)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "SearchImage",
                            tint = Color.White
                        )
                    }
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            if (text.isNotEmpty()) {
                                onTextChange("")
                            } else {
                                onCloseClicked()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Image",
                            tint = Color.White
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearchClicked(text)
                    }
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    cursorColor = Color.White.copy(alpha = ContentAlpha.medium)
                )
            )
        }
    }


    @Composable
    fun DefaultAppBar(
        onSearchClicked: () -> Unit
    ) {
        TopAppBar(
            title = {
                Text(text = "Search on GitHub", style = MaterialTheme.typography.body1)
            },
            actions = {
                IconButton(onClick = { onSearchClicked() }) {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = "Search Image",
                        tint = Color.White
                    )
                }
            }
        )
    }


    @Composable
    fun AppBarStates(
        searchState: SearchState,
        searchTextState: String,
        onTextChange: (String) -> Unit,
        onCloseClicked: () -> Unit,
        onSearchClicked: (String) -> Unit,
        onSearchTriggered: () -> Unit
    ) {
        when (searchState) {
            SearchState.CLOSED -> {
                DefaultAppBar(
                    onSearchClicked = onSearchTriggered
                )
            }
            SearchState.OPENED -> {
                SearchAppBar(
                    text = searchTextState,
                    onTextChange = onTextChange,
                    onCloseClicked = onCloseClicked,
                    onSearchClicked = onSearchClicked
                )
            }
        }
    }


    /* private fun setupUI() {
         val searchView = viewBinding?.searchView
         mainTabLayout = viewBinding!!.tabLayout

         searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
             override fun onQueryTextSubmit(q: String): Boolean {
                 lifecycleScope.launch {
                     repeatOnLifecycle(Lifecycle.State.STARTED) {
                         when (mainTabLayout.selectedTabPosition) {
                             0 -> {
                                 pagerSharedViewModel.onIntent(PagerIntent.SearchGitListSortedByStars(q))
                             }
                             1 -> {
                                 pagerSharedViewModel.onIntent(
                                     PagerIntent.SearchGitListSortedByUpdate(q))
                             }
                             else -> {
                                 Timber.i("MainPagerFragment, searchView.setOnQueryTextListener - something wrong")
                             }
                         }}
                 }
                 return true
             }
             override fun onQueryTextChange(q: String?): Boolean = true
         })
     }*/

    /* private fun setupPager() {
         val pager = viewBinding!!.viewPager
         val adapter = ViewPagerAdapter(requireActivity())
         pager.adapter = adapter

         TabLayoutMediator(mainTabLayout, pager) { tab, position ->
             when (position) {
                 0 -> { tab.text = "Sort by Stars" }
                 1 -> { tab.text = "Sort by Update"}
             }
         }.attach()
     }*/
}