package com.snowtouch.core.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.snowtouch.core.domain.repository.DatabaseReferenceManager

class DatabaseReferenceManagerImpl(
    db : FirebaseDatabase,
    private val auth : FirebaseAuth
) : DatabaseReferenceManager {

    override val currentUserId : String = auth.currentUser?.uid ?: ""

    override val groups : DatabaseReference = db.getReference("groups")

    override val groupsPreview : DatabaseReference = db.getReference("groups_preview")

    override val users : DatabaseReference = db.getReference("users")

    override val userNamesList : DatabaseReference = db.getReference("user_names")

    override val currentUserGroupsIds : DatabaseReference =
        db.getReference("user_groups_ids").child(currentUserId)

    override val currentUserActiveAdsIds : DatabaseReference =
        db.getReference("user_active_ads_ids").child(currentUserId)

    override val currentUserFinishedAdsIds : DatabaseReference =
        db.getReference("user_finished_ads_ids").child(currentUserId)

    override val currentUserRecentlyViewedAdsIds : DatabaseReference =
        db.getReference("user_recent_ads_ids").child(currentUserId)

    override val currentUserFavoriteAdsIds : DatabaseReference =
        db.getReference("user_favorite_ads_ids").child(currentUserId)

    override val currentUserGroupsIdNamesPairs : DatabaseReference =
        db.getReference("user_groups_ids_names").child(currentUserId)

    override val groupUsersCounter : DatabaseReference = db.getReference("group_users_counter")

    override val groupAdsCounter : DatabaseReference  = db.getReference("group_ads_counter")

    override val groupUserIdList : DatabaseReference = db.getReference("group_userId_list")

    override val groupUserNames : DatabaseReference = db.getReference("group_userNames_list")

    override val groupAdsIdList : DatabaseReference = db.getReference("group_adsId_list")

    override val advertisements : DatabaseReference = db.getReference("ads")

    override val advertisementsPreview : DatabaseReference = db.getReference("ads_preview")

}