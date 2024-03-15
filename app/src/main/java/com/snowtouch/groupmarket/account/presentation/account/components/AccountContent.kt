package com.snowtouch.groupmarket.account.presentation.account.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.snowtouch.groupmarket.account.navigation.AccountRoutes
import com.snowtouch.groupmarket.account.presentation.account.CategoryOptionItem
import com.snowtouch.groupmarket.account.presentation.account.CategoryTitleText
import com.snowtouch.groupmarket.account.presentation.accountScreenCategories
import com.snowtouch.groupmarket.account.presentation.active_ads.ActiveAdsScreen
import com.snowtouch.groupmarket.core.presentation.components.ext.adaptiveColumnWidth
import com.snowtouch.groupmarket.core.presentation.util.DisplaySize

@Composable
fun AccountContent(
    displaySize : DisplaySize,
    onSignOutButtonClick : () -> Unit,
    onAccountOptionClick : (String) -> Unit,
    modifier : Modifier = Modifier,
    onAdPreviewClick : (String) -> Unit = {},
) {
    var selectedOption by rememberSaveable { mutableStateOf("") }

    Row(modifier = modifier) {
        Column(
            modifier.adaptiveColumnWidth()
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
                        onClick = { onSignOutButtonClick() },
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
                            onOptionClickNavigate = {
                                if (displaySize == DisplaySize.Compact)
                                    onAccountOptionClick(categoryOption.route)
                                else selectedOption = categoryOption.route
                            }
                        )
                    }
                }
            }
        }

        AnimatedVisibility(visible = displaySize == DisplaySize.Extended) {
            when (selectedOption) {
                "" -> Box(modifier = modifier)

                AccountRoutes.ActiveAds.route -> ActiveAdsScreen(
                    navigateToAdDetails = { adId ->
                        onAdPreviewClick(adId)
                    }
                )

                AccountRoutes.FinishedAds.route -> TODO()

                AccountRoutes.DraftAds.route -> TODO()
            }
        }
    }
}