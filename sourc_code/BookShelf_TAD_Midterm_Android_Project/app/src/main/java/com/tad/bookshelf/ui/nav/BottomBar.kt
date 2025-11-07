package com.tad.bookshelf.ui.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(nav: NavHostController) {
    // Mỗi item: (route, label, icon)
    val items = listOf(
        Triple(Routes.SEARCH,  "Tìm kiếm", Icons.Default.Search),
        Triple(Routes.LIBRARY, "Thư viện", Icons.Default.FavoriteBorder)
    )

    val currentBackStackEntry by nav.currentBackStackEntryAsState()
    val route = currentBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { (r, label, icon) ->
            NavigationBarItem(
                selected = route?.startsWith(r) == true,
                onClick = {
                    nav.navigate(r) {
                        popUpTo(nav.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(icon, contentDescription = null) },
                label = { Text(label) }
            )
        }
    }
}
