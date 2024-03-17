package com.snowtouch.account_feature.presentation.account.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.snowtouch.account_feature.presentation.AccountViewModel
import com.snowtouch.core.presentation.util.DisplaySize
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun Account(
    displaySize : DisplaySize,
    navigateBack : () -> Unit,
    navigateToLogin : () -> Unit,
    onAccountOptionClick : (String) -> Unit,
    navigateToAdDetails : (String) -> Unit,
    modifier : Modifier = Modifier,
    viewModel : AccountViewModel = koinViewModel()
) {
    AccountContent(
        displaySize = displaySize,
        navigateBack = navigateBack,
        onSignOutButtonClick = {
            viewModel.signOut()
            navigateToLogin() },
        navigateToAccountOption = onAccountOptionClick,
        modifier = modifier,
        navigateToAdDetails = navigateToAdDetails
    )
}