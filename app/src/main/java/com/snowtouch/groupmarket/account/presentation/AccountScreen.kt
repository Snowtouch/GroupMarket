package com.snowtouch.groupmarket.account.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AccountScreen(
    viewModel: AccountScreenViewModel,
    onNavigateToOptionClick: (String) -> Unit,
    onSignOutNavigate: () -> Unit
){
    AccountScreenContent(
        onSignOutButtonClick =  viewModel::signOut,
        onOptionClickNavigate = onNavigateToOptionClick,
        onSignOutNavigate = onSignOutNavigate
    )
}
@Composable
fun AccountScreenContent(
    onSignOutButtonClick: () -> Unit,
    onOptionClickNavigate: (String) -> Unit,
    onSignOutNavigate: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            ElevatedButton(
                onClick = {
                    onSignOutButtonClick()
                    onSignOutNavigate()
                },
                modifier = Modifier.size(width = 95.dp, height = 40.dp),
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = "Logout",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        accountScreenCategories.forEach { category ->
            CategoryTitleText(accountScreenCategory = category)
            category.accountCategoryOptions?.forEach { categoryOption ->
                CategoryOptionItem(
                    accountCategoryOption = categoryOption,
                    onOptionClickNavigate = onOptionClickNavigate
                )
            }
        }
    }
}
@Composable
fun CategoryTitleText(accountScreenCategory: AccountScreenCategory) {
    Row(
        modifier = Modifier
            .padding(top = 15.dp)
    ) {
        Text(text = accountScreenCategory.name, style = MaterialTheme.typography.headlineLarge)
    }
}
@Composable
fun CategoryOptionItem(
    accountCategoryOption: AccountCategoryOption,
    onOptionClickNavigate: (String) -> Unit
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
    Divider()
}
@Preview
@Composable
fun AccountScreenPreview() {
    AccountScreenContent(
        {},
        {},
        {}
    )
}