@file:OptIn(ExperimentalFoundationApi::class)

package com.snowtouch.feature_advertisement_details.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.layout.lerp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.snowtouch.core.data.SamplePreviewData
import com.snowtouch.core.domain.model.Advertisement
import com.snowtouch.core.presentation.components.CommonButton
import com.snowtouch.core.presentation.components.ext.adaptiveColumnWidth
import com.snowtouch.core.presentation.components.theme.GroupMarketTheme
import com.snowtouch.core.presentation.util.timestampToDate
import com.snowtouch.feature_advertisement_details.R
import kotlin.math.absoluteValue

@Composable
fun AdvertisementDetailContent(
    advertisement : Advertisement,
    isFavorite : Boolean,
    onFavoriteButtonClick : (String) -> Unit,
    onContactSellerClick : (String) -> Unit,
    onReserveItemClick : (String) -> Unit,
    modifier : Modifier = Modifier,
) {
    val verticalScrollState = rememberScrollState(0)

    Column(
        modifier = modifier
            .padding(horizontal = 6.dp)
            .adaptiveColumnWidth()
            .verticalScroll(state = verticalScrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            AdImageCarousel(
                adTitle = advertisement.title ?: "",
                adImages = advertisement.images ?: emptyList()
            )
            IconButton(
                onClick = { onFavoriteButtonClick(advertisement.uid ?: "") },
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite
                    else Icons.Outlined.FavoriteBorder,
                    contentDescription = "favorite toggle",
                    modifier = Modifier.scale(1.5f)
                )
            }
        }
        ElevatedCard {
            Column(modifier = Modifier.padding(6.dp)) {
                Text(
                    text = "Added: " + timestampToDate(advertisement.postDateTimestamp ?: 0),
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "${advertisement.title}",
                    style = MaterialTheme.typography.titleLarge
                )
                HorizontalDivider(
                    thickness = 8.dp,
                    color = MaterialTheme.colorScheme.surface
                )
                Text(
                    text = "Price: ${advertisement.price}",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineSmall
                )


                Text(
                    text = "Group: ${advertisement.groupName}",
                    style = MaterialTheme.typography.labelMedium
                )


            }
            HorizontalDivider(
                thickness = 8.dp,
                color = MaterialTheme.colorScheme.surface
            )
            Column(modifier = Modifier.padding(6.dp)) {
                Text(text = "Description", fontWeight = FontWeight.Bold)
                Text(text = "${advertisement.description}")
            }
        }
        CommonButton(
            onClick = { onContactSellerClick(advertisement.uid ?: "") },
            text = "Contact seller"
        )
        CommonButton(
            onClick = { onReserveItemClick(advertisement.uid ?: "") },
            text = "Reserve"
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AdImageCarousel(
    adTitle : String,
    adImages : List<String>,
    modifier : Modifier = Modifier,
) {

    val pagerState = rememberPagerState(pageCount = { adImages.size })

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 48.dp),
            pageSpacing = 16.dp
        ) { page ->
            AsyncImage(
                model = if (adImages.isNotEmpty()) adImages[page]
                else painterResource(id = R.drawable.placeholder_image),
                modifier = Modifier
                    .carouselTransition(page, pagerState)
                    .fillMaxSize(),
                contentDescription = adTitle,
                placeholder = painterResource(R.drawable.placeholder_image),
                contentScale = ContentScale.FillWidth
                )
        }
        DotIndicators(
            pageCount = adImages.size,
            pagerState = pagerState,
            modifier = Modifier
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DotIndicators(
    pageCount : Int,
    pagerState : PagerState,
    modifier : Modifier,
) {
    val selectedColor = Color.DarkGray
    val unselectedColor = Color.Gray

    Row(modifier = modifier) {
        repeat(pageCount) { iteration ->
            val color = if (pagerState.currentPage == iteration) selectedColor else unselectedColor
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun Modifier.carouselTransition(page : Int, pagerState : PagerState) =
    graphicsLayer {
        val pageOffset =
            ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

        val transformation =
            lerp(
                start = ScaleFactor(0.7f, 0.7f),
                stop = ScaleFactor(1f, 1f),
                fraction = 1f - pageOffset.coerceIn(0f, 1f)
            )
        alpha = transformation.scaleX
        scaleY = transformation.scaleY
    }

@Preview(showSystemUi = true)
@Composable
fun AdvertisementDetailScreenPreview() {
    GroupMarketTheme {
        AdvertisementDetailContent(
            advertisement = SamplePreviewData.sampleAd3Details,
            isFavorite = false,
            modifier = Modifier,
            onFavoriteButtonClick = {},
            onContactSellerClick = {},
            onReserveItemClick = {}
        )
    }
}