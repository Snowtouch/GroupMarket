package com.snowtouch.feature_groups.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.snowtouch.core.presentation.components.VerticalDivider
import com.snowtouch.core.presentation.components.ext.cardContentPadding

@Composable
fun GroupCard(
    modifier: Modifier = Modifier,
    groupId: String,
    groupName: String,
    groupOwner: String,
    groupDescription: String,
    numberOfAdvertisements: Int,
    numberOfMembers: Int,
    onGoToGroupAdsClick: (String) -> Unit

) {
    var expanded by remember { mutableStateOf(false) }

    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(6.dp),
        onClick = { expanded = !expanded },
        shape = MaterialTheme.shapes.small
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                modifier = modifier
                    .height(IntrinsicSize.Min)
                    .cardContentPadding(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier.weight(1f)) {
                    Text(
                        text = groupName,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(text = "Owner: $groupOwner")
                }
                Column(modifier.padding(4.dp)) {
                    Text(text = "Advertisements: $numberOfAdvertisements")
                    Text(text = "Members: $numberOfMembers")
                }
                Box {
                    VerticalDivider()
                    IconButton(
                        onClick = { onGoToGroupAdsClick(groupId) }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "View details"
                        )
                    }
                }
            }
            AnimatedVisibility(visible = expanded) {
                Column {
                    Text(text = groupDescription)
                }
            }
            HorizontalDivider()
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = null,
                modifier = Modifier
                    .rotate(if (!expanded) 0f else 180f))
        }
    }
}