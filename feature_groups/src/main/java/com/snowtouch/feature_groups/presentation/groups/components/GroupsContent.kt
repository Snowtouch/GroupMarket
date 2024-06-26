package com.snowtouch.feature_groups.presentation.groups.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.snowtouch.core.data.SamplePreviewData
import com.snowtouch.core.domain.model.GroupPreview
import com.snowtouch.feature_groups.presentation.components.GroupCard

@Composable
fun GroupsContent(
    userGroupsList : List<GroupPreview>,
    onGoToGroupAdsClick : (String) -> Unit,
    modifier : Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        if (userGroupsList.isNotEmpty()) {
            items(items = userGroupsList) { group ->
                GroupCard(
                    groupId = group.uid ?: "",
                    groupName = group.name ?: "",
                    groupOwner = group.ownerName ?: "",
                    groupDescription = group.description ?: "",
                    numberOfAdvertisements = group.advertisementsCount ?: 0,
                    numberOfMembers = group.membersCount ?: 1,
                    onGoToGroupAdsClick = onGoToGroupAdsClick
                )
            }
        } else {
            item {
                Box(
                    modifier = modifier,
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "You are not part of any group")
                }
            }
        }
    }
}

@Preview
@Composable
private fun GroupsContentPrev() {
    GroupsContent(
        userGroupsList = listOf(SamplePreviewData.sampleGroupPreview),
        onGoToGroupAdsClick = {})
}