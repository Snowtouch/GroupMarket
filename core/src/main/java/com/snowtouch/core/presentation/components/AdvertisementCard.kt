package com.snowtouch.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.snowtouch.core.R
import com.snowtouch.core.data.SamplePreviewData
import com.snowtouch.core.domain.model.AdvertisementPreview
import com.snowtouch.core.presentation.util.timestampToDate
import kotlin.math.tan

@Composable
fun AdvertisementCard(
    advertisement : AdvertisementPreview,
    isFavorite : Boolean,
    onCardClick : () -> Unit,
    onFavoriteButtonClick : (String) -> Unit,
) {
    ElevatedCard(
        onClick = onCardClick,
        modifier = Modifier
            .size(width = 250.dp, height = 300.dp)
            .border(
                BorderStroke(1.dp, Color.Black.copy(alpha = 0.2f)),
                shape = MaterialTheme.shapes.small
            ),
        shape = MaterialTheme.shapes.small
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(
                            if (advertisement.image?.isEmpty() == true) {
                                R.drawable.placeholder_image
                            } else {
                                advertisement.image
                            }
                        )
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.placeholder_image),
                    contentDescription = "Ad image",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                )
                SetFavoriteButton(
                    isFavorite = isFavorite,
                    advertisementId = advertisement.uid ?: "",
                    onFavoriteButtonClick = onFavoriteButtonClick,
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
            Box(modifier = Modifier) {
                Text(
                    text = advertisement.title ?: "",
                    modifier = Modifier,
                    fontSize = 20.sp,
                    overflow = TextOverflow.Ellipsis,
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
                    text = "$ ${advertisement.price}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Text(
                    text = timestampToDate(advertisement.postDateTimestamp ?: 0),
                    fontSize = 12.sp,
                )
            }
        }
    }
}

@Composable
fun SetFavoriteButton(
    isFavorite : Boolean,
    advertisementId : String,
    onFavoriteButtonClick : (String) -> Unit,
    modifier : Modifier = Modifier,
) {
    IconButton(
        onClick = { onFavoriteButtonClick(advertisementId) },
        modifier = modifier
    ) {
        Icon(
            imageVector = if (isFavorite)
                Icons.Filled.Favorite
            else
                Icons.Outlined.FavoriteBorder,
            contentDescription = "Favorite toggle button",
            modifier = Modifier,
        )
    }
}

class Parallelogram(private val angle: Float) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(

            Path().apply {
                val radian = (90 - angle) * Math.PI / 180
                val xOnOpposite = (size.height * tan(radian)).toFloat()
                moveTo(0f, size.height)
                lineTo(x = xOnOpposite, y = 0f)
                lineTo(x = size.width, y = 0f)
                lineTo(x = size.width - xOnOpposite, y = size.height)
                lineTo(x = xOnOpposite, y = size.height)
            }
        )
    }
}

@Preview
@Composable
fun CardPreview() {
    AdvertisementCard(
        advertisement = SamplePreviewData.sampleAd1Preview,
        isFavorite = true,
        onCardClick = {},
        onFavoriteButtonClick = {})
}