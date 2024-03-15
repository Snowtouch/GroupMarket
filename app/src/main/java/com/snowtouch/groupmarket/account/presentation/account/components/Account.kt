package com.snowtouch.groupmarket.account.presentation.account.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.snowtouch.groupmarket.account.presentation.AccountViewModel
import com.snowtouch.groupmarket.core.presentation.util.DisplaySize
import org.koin.androidx.compose.koinViewModel

@Composable
fun Account(
    displaySize : DisplaySize,
    onSignOutButtonClick : () -> Unit,
    onAccountOptionClick : (String) -> Unit,
    modifier : Modifier = Modifier,
    viewModel : AccountViewModel = koinViewModel(),
) {
    AccountContent(
        displaySize = displaySize,
        onSignOutButtonClick = onSignOutButtonClick,
        onAccountOptionClick = onAccountOptionClick,
        modifier = modifier,
    )
}