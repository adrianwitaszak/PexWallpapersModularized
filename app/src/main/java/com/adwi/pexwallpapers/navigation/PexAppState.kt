package com.adwi.pexwallpapers.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

object MainDestinations {
    const val HOME_ROUTE = "home"
    const val WALLPAPER_PREVIEW_ROUTE = "wallpaper_preview"
    const val WALLPAPER_ID_KEY = "wallpaperId"
    const val SEARCH_ROUTE = "search_route"
    const val SEARCH_QUERY = "query"
}

@ExperimentalAnimationApi
@Composable
fun rememberMyAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberAnimatedNavController()
) =
    remember(scaffoldState, navController) {
        PexAppState(scaffoldState, navController)
    }

@Stable
class PexAppState(
    val scaffoldState: ScaffoldState,
    val navController: NavHostController,
) {
    // ----------------------------------------------------------
    // BottomBar state source of truth
    // ----------------------------------------------------------

    val bottomBarTabs = HomeSections.values()
    private val bottomBarRoutes = bottomBarTabs.map { it.route }

    // Reading this attribute will cause recompositions when the bottom bar needs shown, or not.
    // Not all routes need to show the bottom bar.
    val shouldShowBottomBar: Boolean
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination?.route in bottomBarRoutes
    // ----------------------------------------------------------
    // Navigation state source of truth
    // ----------------------------------------------------------

    val currentRoute: String?
        get() = navController.currentDestination?.route

    fun upPress() {
        navController.navigateUp()
    }

    fun popBackTo(route: String) {
        navController.navigate(route) {
            popUpTo(route) { inclusive = true }
        }
    }

    fun navigateToBottomBarRoute(route: String) {
        if (route != currentRoute) {
            navController.navigate(route) {
                launchSingleTop = true
                restoreState = true
                popUpTo(findStartDestination(navController.graph).id) {
                    saveState = true
                }
            }
        }
    }

    fun navigateToPreview(wallpaperId: Int, from: NavBackStackEntry) {
        if (from.lifecycleIsResumed()) {
            navController.navigate("${MainDestinations.WALLPAPER_PREVIEW_ROUTE}/$wallpaperId")
        }
    }

    fun navigateToCategory(categoryName: String, from: NavBackStackEntry) {
        if (from.lifecycleIsResumed()) {
            navController.navigate("${MainDestinations.SEARCH_ROUTE}/$categoryName")
        }
    }
}