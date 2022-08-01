package com.quadrantapp.core.base

object Constants {
    // Notification Channel constants

    // Name of Notification Channel for verbose notifications of background work
    @JvmField val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence =
        "Verbose WorkManager Notifications"
    const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
        "Shows notifications whenever work starts"
    @JvmField val NOTIFICATION_TITLE: CharSequence = "WorkRequest Starting"
    const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
    const val NOTIFICATION_ID = 1

    // work manager
    const val GET_CURRENT_PRICE_UNIQUE_NAME = "GET_CURRENT_PRICE_UNIQUE_NAME"
    const val TAG_GET_CURRENT_PRICE = "TAG_GET_CURRENT_PRICE"
}