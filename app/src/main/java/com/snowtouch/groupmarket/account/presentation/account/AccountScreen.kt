package com.snowtouch.groupmarket.account.presentation.account

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.snowtouch.groupmarket.account.presentation.AccountCategoryOption
import com.snowtouch.groupmarket.account.presentation.AccountScreenCategory
import com.snowtouch.groupmarket.account.presentation.account.components.Account
import com.snowtouch.groupmarket.core.presentation.components.BottomNavigationBar
import com.snowtouch.groupmarket.core.presentation.components.NavigationRail
import com.snowtouch.groupmarket.core.presentation.components.ScaffoldTemplate
import com.snowtouch.groupmarket.core.presentation.util.DisplaySize

@Composable
fun AccountScreen(
    displaySize : DisplaySize,
    navigateToAccountOption : (String) -> Unit,
    onNavBarIconClick : (String) -> Unit,
    onSignOutNavigate : () -> Unit,
) {
    ScaffoldTemplate(
        bottomBar = {
            when (displaySize) {
                DisplaySize.Compact -> BottomNavigationBar(onNavItemClick = onNavBarIconClick)
                DisplaySize.Extended -> NavigationRail(onNavItemClick = onNavBarIconClick)
            }
        }
    ) { innerPadding ->
        Account(
            displaySize = displaySize,
            onSignOutButtonClick = onSignOutNavigate,
            onAccountOptionClick = navigateToAccountOption,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun CategoryTitleText(accountScreenCategory : AccountScreenCategory) {
    Row(
        modifier = Modifier
            .padding(top = 15.dp)
    ) {
        Text(text = accountScreenCategory.name, style = MaterialTheme.typography.headlineLarge)
    }
}

@Composable
fun CategoryOptionItem(
    accountCategoryOption : AccountCategoryOption,
    onOptionClickNavigate : (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .clickable(onClick = { onOptionClickNavigate(accountCategoryOption.name) }),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (accountCategoryOption.icon != null) {
            Icon(imageVector = accountCategoryOption.icon, contentDescription = null)
        }
        Spacer(modifier = Modifier.padding(6.dp))
        Text(text = accountCategoryOption.name, style = MaterialTheme.typography.titleMedium)
    }
    HorizontalDivider()
}