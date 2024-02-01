package com.snowtouch.groupmarket.common.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.snowtouch.groupmarket.R
import com.snowtouch.groupmarket.model.Advertisement

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdvertisementCard(
    advertisement: Advertisement,
    favoritesList: List<String> = emptyList(),
    onCardClick: () -> Unit,
    onFavoriteButtonClick: (String) -> Unit
) {
    Card(
        onClick = onCardClick,
        modifier = Modifier
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(advertisement.images.first())
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.placeholder_image),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                )
            }
            Divider()
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = advertisement.title,
                    modifier = Modifier.padding(12.dp),
                    fontSize = 20.sp,
                    overflow = TextOverflow.Clip,
                    softWrap = true,
                    maxLines = 2
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = advertisement.price,
                    modifier = Modifier.padding(8.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.End
                    ) {
                    SetFavoriteButton(
                        favoritesList = favoritesList,
                        advertisementId = advertisement.uid,
                        onFavoriteButtonClick = onFavoriteButtonClick
                    )
                    Text(
                        text = advertisement.postDate,
                    )
                }
            }
        }
    }
}
@Composable
fun SetFavoriteButton(
    favoritesList: List<String>? = emptyList(),
    advertisementId: String,
    onFavoriteButtonClick: (String) -> Unit
) {
    IconButton(onClick = { onFavoriteButtonClick(advertisementId) }) {
        Icon(
            imageVector = if (favoritesList?.contains(advertisementId) == true)
                Icons.Filled.Favorite
            else
                Icons.Outlined.FavoriteBorder,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
fun CardPreview(){
    val list: List<String> = listOf(R.drawable.sample_ad_image.toString())
    val userFavorites = listOf("2")
    AdvertisementCard(
        Advertisement(
            uid = "1",
            groupId = "2",
            title = "aaaaaaaaa",
            images = list,
            description = "askooocood",
            price = "344",
            postDate = "13-10-2023"),
        userFavorites,
        {}
    ) {}
}