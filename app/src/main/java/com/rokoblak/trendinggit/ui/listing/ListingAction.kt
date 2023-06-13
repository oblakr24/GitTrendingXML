package com.rokoblak.trendinggit.ui.listing


sealed interface ListingAction {
    object RefreshTriggered : ListingAction
    object NextPageTriggerReached : ListingAction
}