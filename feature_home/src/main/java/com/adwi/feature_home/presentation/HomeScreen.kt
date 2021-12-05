package com.adwi.feature_home.presentation

import androidx.compose.compiler.plugins.kotlin.lower.defaultsBitIndex
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.Header
import com.adwi.components.PexScaffold
import com.adwi.components.theme.Dimensions.BottomBar.BottomNavHeight
import com.adwi.components.theme.paddingValues
import com.adwi.feature_home.R
import com.adwi.feature_home.presentation.components.CategoryListHorizontalPanel
import com.adwi.feature_home.presentation.components.DailyWallpaper
import com.adwi.feature_home.presentation.components.WallpaperListHorizontalPanel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import timber.log.Timber

@ExperimentalPagerApi
@ExperimentalCoroutinesApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@ExperimentalPagingApi
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onWallpaperClick: (Int) -> Unit,
    onCategoryClick: (String) -> Unit,
    navigateToSearch: () -> Unit
) {
    val daily by viewModel.daily.collectAsState()
    val colors by viewModel.colors.collectAsState()
    val curated by viewModel.curated.collectAsState()
    val lowRes = viewModel.lowRes

    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val pendingScrollToTopAfterRefresh by viewModel.pendingScrollToTopAfterRefresh.collectAsState()

    val homeListState = rememberLazyListState(initialFirstVisibleItemIndex = 0)
    val pagerState = rememberPagerState()
    val colorsListState = rememberLazyListState()
    val curatedListState = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope()

    if (pendingScrollToTopAfterRefresh) {
        LaunchedEffect(pendingScrollToTopAfterRefresh) {
            coroutineScope.launch {
                homeListState.animateScrollToItem(0)
                pagerState.animateScrollToPage(0)
                colorsListState.animateScrollToItem(0)
                curatedListState.animateScrollToItem(0)
                viewModel.setPendingScrollToTopAfterRefresh(false)
            }
        }
    }
    val isOnTop = homeListState.firstVisibleItemIndex == 0
    val elevation by remember { mutableStateOf(!isOnTop) }

    if (elevation) {
        Timber.tag("Header").d("toolbar is elevated")
    } else {
        Timber.tag("Header").d("toolbar is flat")
    }

    PexScaffold(
        viewModel = viewModel
    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = { viewModel.manualRefresh() }
        ) {
            LazyColumn(
                state = homeListState,
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(
                    bottom = BottomNavHeight + paddingValues
                )
            ) {
                item {
                    Header(
                        title = stringResource(id = R.string.home),
                        onActionClick = navigateToSearch
                    )
                }
                item {
                    daily?.let { list ->
                        DailyWallpaper(
                            pagerState = pagerState,
                            modifier = Modifier
                                .padding(vertical = paddingValues / 2),
                            dailyList = list,
                            onWallpaperClick = { id -> onWallpaperClick(id) },
                            onLongPress = { viewModel.onFavoriteClick(it) },
                            lowRes = lowRes
                        )
                    }
                }
                item(defaultsBitIndex(2)) {
                    colors?.let { list ->
                        CategoryListHorizontalPanel(
                            panelTitle = stringResource(id = R.string.colors),
                            listState = colorsListState,
                            colors = list,
                            onCategoryClick = { onCategoryClick(it) }
                        )
                    }
                }
                item(defaultsBitIndex(3)) {
                    curated?.let { list ->
                        val categoryName = stringResource(id = R.string.curated)
                        WallpaperListHorizontalPanel(
                            panelName = categoryName,
                            wallpapers = list,
                            listState = curatedListState,
                            onWallpaperClick = { id -> onWallpaperClick(id) },
                            onLongPress = { viewModel.onFavoriteClick(it) },
                            onShowMoreClick = navigateToSearch
                        )
                    }
                }
            }
        }
    }
}