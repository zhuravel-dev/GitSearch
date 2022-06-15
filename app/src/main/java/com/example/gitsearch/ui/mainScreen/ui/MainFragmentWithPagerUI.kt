import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.gitsearch.data.local.model.ItemLocalModel
import com.example.gitsearch.ui.compose.CircularProgress
import com.example.gitsearch.ui.compose.ErrorDialog
import com.example.gitsearch.ui.compose.WelcomeText
import com.example.gitsearch.ui.mainScreen.MainIntent
import com.example.gitsearch.ui.mainScreen.MainState
import com.example.gitsearch.ui.mainScreen.MainViewModel
import com.example.gitsearch.ui.mainScreen.ui.SetupPager
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class, androidx.paging.ExperimentalPagingApi::class,
    kotlinx.coroutines.ExperimentalCoroutinesApi::class)
@Composable
fun MainFragmentWithPagerUI(viewModel: MainViewModel, onClick: (ItemLocalModel) -> Unit) {

    val resultState by viewModel.state.collectAsState()
    val pages = remember { listOf("Sorting by stars", "Sorting by update") }
    val pagerState = rememberPagerState(pageCount = pages.size)
    val textState = remember { mutableStateOf("") }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        val (topAppBar, tabs, pager, welcomeText, progress, error) = createRefs()

        when (resultState) {
            is MainState.Idle -> {
                WelcomeText(
                    welcomeText,
                    topAppBar
                )
            }

            is MainState.Loading -> {
                CircularProgress(progress)
            }

            is MainState.DataLoaded -> {
                val listStars =
                    (resultState as MainState.DataLoaded).dataSortedByStars.collectAsLazyPagingItems()
                val listUpdate =
                    (resultState as MainState.DataLoaded).dataSortedByUpdate.collectAsLazyPagingItems()

                this@ConstraintLayout.SetupPager(
                    topAppBar,
                    tabs,
                    pager,
                    pagerState = pagerState,
                    userListByStars = listStars,
                    userListByUpdate = listUpdate,
                    onClick = {
                        onClick(it)
                    })
            }
            is MainState.Error -> ErrorDialog(error, onRetry = { viewModel.onIntent(MainIntent.SearchGitList(textState.value)) })
        }
    }
}