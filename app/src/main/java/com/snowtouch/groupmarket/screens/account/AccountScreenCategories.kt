package com.snowtouch.groupmarket.screens.account

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

val accountScreenCategories = listOf(
    AccountScreenCategory(
        name = "Your advertisements",
        accountCategoryOptions = listOf(
            AccountCategoryOption(name = "Current", icon = Icons.Default.ShoppingCart, route = "current_ads"),
            AccountCategoryOption(name = "Finished", icon = Icons.Default.Done, route = "finished_ads"),
            AccountCategoryOption(name = "Drafts", icon = Icons.Default.Build, route = "draft_ads")
        )
    ),
    AccountScreenCategory(
        name = "Settings",
        accountCategoryOptions = listOf(
            AccountCategoryOption(name = "Account data", icon = Icons.Default.Person, route = "account_data"),
            AccountCategoryOption(name = "Theme", icon = Icons.Default.Edit, route = "theme_options"),
            AccountCategoryOption(name = "About", icon = Icons.Default.Info, route = "about_app")
        )
    )
)
data class AccountCategoryOption(
    val name: String,
    val icon: ImageVector?,
    val route: String
)
data class AccountScreenCategory(
    val name: String,
    val accountCategoryOptions: List<AccountCategoryOption>?
)