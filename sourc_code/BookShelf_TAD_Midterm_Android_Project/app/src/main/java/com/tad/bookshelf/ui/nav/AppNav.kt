package com.tad.bookshelf.ui.nav

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tad.bookshelf.ui.screen.DetailScreen
import com.tad.bookshelf.ui.screen.LibraryScreen
import com.tad.bookshelf.ui.screen.PreviewScreen
import com.tad.bookshelf.ui.screen.SearchScreen
import com.tad.bookshelf.ui.viewmodel.DetailViewModel
import com.tad.bookshelf.ui.viewmodel.LibraryViewModel
import com.tad.bookshelf.ui.viewmodel.SearchBookViewModel

// ui/nav/AppNav.kt
@Composable
fun AppNav(
    searchVM: SearchBookViewModel,
    libraryVM: LibraryViewModel,
    detailViewModel: DetailViewModel
) {
    val nav = rememberNavController()
    Scaffold(
        bottomBar = { BottomBar(nav)}
    ) { inner ->
        NavHost(
            navController = nav,
            startDestination = Routes.SEARCH,
            modifier = Modifier
                .padding(inner)     // padding từ Scaffold ngoài (có BottomBar)
                .fillMaxSize(),
        ) {
            composable(Routes.SEARCH) {
                SearchScreen(
                    nav = nav,
                    searchVM = searchVM,
                    onOpenDetail = { id -> nav.navigate(Routes.detail(id)) }
                )
            }
            composable(Routes.LIBRARY) {
                LibraryScreen(
                    nav = nav,
                    vm = libraryVM,
                    onOpenDetail = { id -> nav.navigate(Routes.detail(id)) }
                )
            }
            composable(
                route = Routes.DETAIL,
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) { backStack ->
                val id = backStack.arguments!!.getString("id")!!
                DetailScreen(
                    id = id,
                    onBack = { nav.popBackStack() },
                    onPreview = { url -> nav.navigate(Routes.preview(url)) },
                    vm = detailViewModel
                )
            }
            composable(
                route = Routes.PREVIEW,
                arguments = listOf(navArgument("url") { type = NavType.StringType; nullable = true })
            ) { back ->
                val url = back.arguments?.getString("url") ?: return@composable
                PreviewScreen(url = url, onBack = { nav.popBackStack() })
            }
        }
    }
}
