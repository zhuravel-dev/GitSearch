import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.SemanticsActions.OnClick
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.gitsearch.data.local.model.ItemLocalModel
import com.example.gitsearch.ui.compose.CircularProgress
import com.example.gitsearch.ui.compose.ErrorDialog
import com.example.gitsearch.ui.compose.WelcomeText
import com.example.gitsearch.ui.mainScreen.MainState
import com.example.gitsearch.ui.mainScreen.MainViewModel
import com.example.gitsearch.ui.mainScreen.SetupPager
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState

@OptIn(
    ExperimentalPagerApi::class, androidx.paging.ExperimentalPagingApi::class,
    kotlinx.coroutines.ExperimentalCoroutinesApi::class
)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainFragmentWithPagerUI(viewModel: MainViewModel, onClick: (ItemLocalModel) -> Unit) {

    val resultState by viewModel.state.collectAsState()
    val pages = remember { listOf("Sorting by stars", "Sorting by update") }
    val pagerState = rememberPagerState(pageCount = pages.size)

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEAECEC))
    ) {
        val (topAppBar, pager, welcomeText, progress, error) = createRefs()

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
                    pager,
                    pagerState = pagerState,
                    userListByStars = listStars,
                    userListByUpdate = listUpdate,
                    onClick = {
                       // onClick(it)
                    })
            }
            is MainState.Error -> ErrorDialog(error)
        }
    }
}