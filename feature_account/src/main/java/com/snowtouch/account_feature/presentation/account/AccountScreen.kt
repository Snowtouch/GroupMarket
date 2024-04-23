package com.snowtouch.account_feature.presentation.account

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
import com.snowtouch.account_feature.presentation.AccountCategoryOption
import com.snowtouch.account_feature.presentation.AccountScreenCategory
import com.snowtouch.account_feature.presentation.account.components.Account
import com.snowtouch.core.navigation.NavBarItem
import com.snowtouch.core.presentation.components.AdaptiveNavigationBar
import com.snowtouch.core.presentation.components.SinglePageScaffold
import com.snowtouch.core.presentation.util.DisplaySize

@Composable
internal fun AccountScreen(
    currentScreen : NavBarItem,
    displaySize : DisplaySize,
    navigateBack : () -> Unit,
    navigateToLogin : () -> Unit,
    navigateToAccountOption : (String) -> Unit,
    navigateToAdDetails : (String) -> Unit,
    onNavMenuIconClick : (String) -> Unit,

    ) {
    SinglePageScaffold(
        bottomBar = {
            AdaptiveNavigationBar(
                currentScreen = currentScreen,
                displaySize = displaySize,
                onNavMenuItemClick = onNavMenuIconClick
            )
        }
    ) { innerPadding ->
        Account(
            displaySize = displaySize,
            navigateBack = navigateBack,
            navigateToLogin = navigateToLogin,
            onAccountOptionClick = navigateToAccountOption,
            navigateToAdDetails = navigateToAdDetails,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
internal fun CategoryTitleText(accountScreenCategory : AccountScreenCategory) {
    Row(modifier = Modifier.padding(top = 15.dp)) {
        Text(
            text = accountScreenCategory.name,
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Composable
internal fun CategoryOptionItem(
    accountCategoryOption : AccountCategoryOption,
    onOptionClickNavigate : (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .clickable(
                onClick = { onOptionClickNavigate(accountCategoryOption.name) }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (accountCategoryOption.icon != null) {
            Icon(
                imageVector = accountCategoryOption.icon,
                contentDescription = accountCategoryOption.name
            )
        }
        Spacer(modifier = Modifier.padding(6.dp))
        Text(text = accountCategoryOption.name, style = MaterialTheme.typography.titleMedium)
    }
    HorizontalDivider()
}