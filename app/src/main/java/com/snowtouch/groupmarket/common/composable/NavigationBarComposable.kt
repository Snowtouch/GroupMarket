package com.snowtouch.groupmarket.common.composable

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.snowtouch.groupmarket.R

data class NavBarItem(
    val icon: @Composable () -> Unit,
    val label: String
)

val bottomBarItems = listOf(
    NavBarItem({ Icons.Filled.Home }, "Home"),
    NavBarItem({ painterResource(R.drawable.baseline_group_24) }, "Favorites"),
    NavBarItem({ Icons.Filled.AddCircle }, "Add"),
    NavBarItem({ Icons.Filled.Email }, "Messages"),
    NavBarItem({ Icons.Filled.Person }, "Account")
)

@Composable
fun CompactScreenNavBar() {
    NavigationBar {
        NavigationBarItem(selected = false, onClick = { /*TODO*/ }, icon = { /*TODO*/ })
    }
}

@Composable
fun BigScreenNavBar() {
    NavigationRail {
        NavigationRailItem(selected = false, onClick = { /*TODO*/ }, icon = { /*TODO*/ })
    }
}