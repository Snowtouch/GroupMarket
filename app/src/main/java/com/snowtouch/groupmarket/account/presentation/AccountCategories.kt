package com.snowtouch.groupmarket.account.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.snowtouch.groupmarket.account.navigation.AccountRoutes
import com.snowtouch.groupmarket.account.navigation.AccountSettingsRoutes

val accountScreenCategories = listOf(
    AccountScreenCategory(
        name = "Your advertisements",
        accountCategoryOptions = listOf(
            AccountCategoryOption(
                name = "Active",
                icon = Icons.Default.ShoppingCart,
                route = AccountRoutes.ActiveAds.route
            ),
            AccountCategoryOption(
                name = "Finished",
                icon = Icons.Default.Done,
                route = AccountRoutes.FinishedAds.route
            ),
            AccountCategoryOption(
                name = "Drafts",
                icon = Icons.Default.Build,
                route = AccountRoutes.DraftAds.route
            ),
        )
    ),
    AccountScreenCategory(
        name = "Settings",
        accountCategoryOptions = listOf(
            AccountCategoryOption(
                name = "Account data",
                icon = Icons.Default.Person,
                route = AccountSettingsRoutes.AccountData.route
            ),
            AccountCategoryOption(
                name = "Theme",
                icon = Icons.Default.Edit,
                route = AccountSettingsRoutes.ThemeSettings.route
            ),
            AccountCategoryOption(
                name = "About",
                icon = Icons.Default.Info,
                route = AccountSettingsRoutes.AboutApp.route
            )
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