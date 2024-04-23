package com.snowtouch.core.data

import com.snowtouch.core.R
import com.snowtouch.core.domain.model.Advertisement
import java.time.LocalDateTime
import java.time.ZoneOffset

object SamplePreviewData {
    private const val description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque " +
            "faucibus a nisl in molestie. Curabitur a tortor ipsum. Ut tincidunt ac turpis " +
            "et scelerisque. Maecenas et nunc a risus tincidunt tempor ut non ante. Proin ante " +
            "ante, pellentesque ac finibus a, faucibus a odio. Vestibulum laoreet tellus quis " +
            "diam accumsan, vitae finibus felis vestibulum. In in mollis tortor. Donec ornare " +
            "vulputate tincidunt. Maecenas sollicitudin molestie lorem. Ut bibendum lacus eu" +
            " bibendum mollis."

    val adImagesList1 = listOf(
        R.drawable.adidas_runfalcon.toString(),
        R.drawable.buty_runfalcon_wide_3.toString(),
        R.drawable.mens_sports_shoes.toString(),
        R.drawable.sample_ad_image.toString())

    val adImagesList2 = listOf(
        R.drawable.sample_ad_image.toString(),
        R.drawable.adidas_runfalcon.toString(),
        R.drawable.buty_runfalcon_wide_3.toString(),
        R.drawable.mens_sports_shoes.toString())

    val adImagesList3 = listOf(
        R.drawable.buty_runfalcon_wide_3.toString(),
        R.drawable.sample_ad_image.toString(),
        R.drawable.adidas_runfalcon.toString(),
        R.drawable.mens_sports_shoes.toString())

    val adImagesList4 = listOf(
        R.drawable.mens_sports_shoes.toString(),
        R.drawable.sample_ad_image.toString(),
        R.drawable.adidas_runfalcon.toString(),
        R.drawable.buty_runfalcon_wide_3.toString())

    val sampleAd1Details = Advertisement(
        uid = "1122",
        ownerUid = "1234",
        groupId = "123",
        groupName = "Sample group 1",
        title = "Brand new shoes",
        images = adImagesList1,
        description = description,
        price = "312",
        postDateTimestamp = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli()
    )
    val sampleAd2Details = Advertisement(
        uid = "2233",
        ownerUid = "1234",
        groupId = "123",
        groupName = "Sample group 2",
        title = "Lego 40212, brand new",
        images = adImagesList2,
        description = description,
        price = "79.7",
        postDateTimestamp = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli()
    )
    val sampleAd3Details = Advertisement(
        uid = "3344",
        ownerUid = "234",
        groupId = "1234",
        groupName = "Sample group 2",
        title = "Men's Lifestyle Shoes",
        images = adImagesList3,
        description = description,
        price = "24.7",
        postDateTimestamp = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli()
    )
    val sampleAd4Details = Advertisement(
        uid = "4455",
        ownerUid = "234",
        groupId = "1234",
        groupName = "Sample group 1",
        title = "adidas Men's Running Adistar 2.0 Running",
        images = adImagesList4,
        description = description,
        price = "212.7",
        postDateTimestamp = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli()
    )
    val sampleAd1Preview = sampleAd1Details.toAdvertisementPreview()
    val sampleAd2Preview = sampleAd2Details.toAdvertisementPreview()
    val sampleAd3Preview = sampleAd3Details.toAdvertisementPreview()
    val sampleAd4Preview = sampleAd4Details.toAdvertisementPreview()

    val adPreviewList = listOf(sampleAd1Preview, sampleAd2Preview, sampleAd3Preview, sampleAd4Preview, sampleAd1Preview, sampleAd4Preview)
}