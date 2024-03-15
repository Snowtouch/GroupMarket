@file:OptIn(ExperimentalFoundationApi::class)

package com.snowtouch.groupmarket.advertisement_details.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.layout.lerp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.snowtouch.groupmarket.R
import com.snowtouch.groupmarket.core.domain.model.Advertisement
import java.util.Date
import kotlin.math.absoluteValue

@Composable
internal fun AdvertisementDetailScreen(
    advertisementId: String,
    viewModel: AdvertisementDetailScreenViewModel,
    onNavigateBack: () -> Unit
) {
    AdvertisementDetailScreenContent(
        advertisement = viewModel.getAdvertisementDetails(advertisementId),
        modifier = Modifier,
        onNavigateBack = onNavigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdvertisementDetailScreenContent(
    advertisement: Advertisement,
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit
) {
    TopAppBar(
        title = { },
        navigationIcon = {
            IconButton(
                onClick = { onNavigateBack() }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        }
    )
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AdImageCarousel(
            adImages = advertisement.images ?: emptyList(),
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AdImageCarousel(
    adImages: List<String>,
    modifier: Modifier = Modifier
) {

    val pagerState = rememberPagerState(pageCount = { adImages.size })

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 32.dp),
            pageSpacing = 16.dp
        ) { page ->
            AsyncImage(
                model = adImages[page],
                contentDescription = null,
                placeholder = painterResource(R.drawable.placeholder_image),
                modifier = Modifier.carouselTransition(page, pagerState))
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
    pageCount: Int,
    pagerState: PagerState,
    modifier: Modifier
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
fun Modifier.carouselTransition(page: Int, pagerState: PagerState) =
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

@Preview
@Composable
fun AdDetailScreenContentPreview() {
    AdvertisementDetailScreenContent(
        advertisement = Advertisement(
            uid = "12",
            ownerUid = "432",
            groupId = "334",
            title = "Shoes",
            description = "Description Description Description Description Description Description",
            price = 333.0,
            postDate = Date()
        ), onNavigateBack = {})
}