package com.snowtouch.feature_groups.presentation.groups.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.snowtouch.core.domain.model.Group
import com.snowtouch.core.presentation.components.ext.adaptiveColumnWidth
import com.snowtouch.core.presentation.util.DisplaySize
import com.snowtouch.feature_groups.presentation.components.GroupCard
import com.snowtouch.feature_groups.presentation.group_ads.components.GroupAds

@Composable
fun GroupsContent(
    userGroupsList: List<Group>,
    displaySize : DisplaySize,
    onGoToGroupAdsClick: (String) -> Unit,
    onAdvertisementCardClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedGroup by rememberSaveable { mutableStateOf("") }

    Row(modifier = modifier) {
        LazyColumn(
            modifier = modifier.adaptiveColumnWidth()
        ) {
            items(items = userGroupsList) { group ->
                GroupCard(
                    groupId = group.uid ?: "",
                    groupName = group.name ?: "",
                    groupOwner = group.ownerName ?: "",
                    groupDescription = group.description ?: "",
                    numberOfAdvertisements = group.advertisementsCount ?: 0,
                    numberOfMembers = group.membersCount ?: 1,
                    onGoToGroupAdsClick = {
                        if (displaySize == DisplaySize.Compact) onGoToGroupAdsClick(group.uid!!)
                        else selectedGroup = group.uid!!
                    }
                )
            }
        }
        AnimatedVisibility(visible = displaySize == DisplaySize.Extended) {
            GroupAds(
                groupId = selectedGroup,
                displaySize = displaySize,
                onAdvertisementCardClick = onAdvertisementCardClick
            )
        }
    }
}