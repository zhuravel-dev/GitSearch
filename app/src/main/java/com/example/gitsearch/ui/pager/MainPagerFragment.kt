package com.example.gitsearch.ui.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.gitsearch.ui.compose.theme.AppTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

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
                    SetupUI()
                }
            }
        }
    }


    @OptIn(ExperimentalPagerApi::class)
    @Composable
    private fun SetupUI() {
        var tabPage by remember { mutableStateOf(TabPage.Stars) }
        val pagerState = rememberPagerState(pageCount = TabPage.values().size)
        val scope = rememberCoroutineScope()

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                val (topBar, tabs, pager) = createRefs()

                TopAppBar(modifier = Modifier
                    .constrainAs(topBar) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }) {
                    SearchViewActionBar(viewModel = viewModel)
                }

                Box(
                    Modifier
                        .background(Color.LightGray)
                        .fillMaxWidth()
                        .constrainAs(tabs) {
                            top.linkTo(topBar.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    TabStars(selectedTabIndex = pagerState.currentPage, onSelectedTab = {
                        scope.launch {
                            pagerState.animateScrollToPage(it.ordinal)
                        } })
                }

                Column(
                    modifier = Modifier
                        .constrainAs(pager) {
                            top.linkTo(tabs.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .padding(12.dp),
                ) {

                    HorizontalPager(state = pagerState) { index ->
                        Column(Modifier.fillMaxSize()) {
                            when (index) {
                                0 -> Text(text = "Test1")
                                1 -> Text(text = "Test2")
                            }
                        }
                    }
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