package com.snowtouch.core.domain.repository

import com.google.firebase.database.DatabaseReference

interface DatabaseReferenceManager {
    val currentUserId : String

    val groups : DatabaseReference
    val users : DatabaseReference
    val userNamesList : DatabaseReference
    val currentUserGroupsIds : DatabaseReference
    val currentUserActiveAdsIds : DatabaseReference
    val currentUserFinishedAdsIds : DatabaseReference
    val currentUserRecentAdsIds : DatabaseReference
    val currentUserFavoriteAdsIds : DatabaseReference
    val currentUserGroupsIdNamesPairs : DatabaseReference

    val groupUsersCounter : DatabaseReference
    val groupAdsCounter : DatabaseReference
    val groupUserIdList : DatabaseReference
    val groupUserNames : DatabaseReference
    val groupAdsIdList : DatabaseReference

    val advertisements : DatabaseReference
    val advertisementsPreview : DatabaseReference

}