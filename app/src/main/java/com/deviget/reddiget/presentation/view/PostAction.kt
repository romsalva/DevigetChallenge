package com.deviget.reddiget.presentation.view

/**
 * Representation of actions that cn be performed on post items in the list.
 */
sealed class PostAction {
    object Click : PostAction()
    object Dismiss : PostAction()
    object ClickThumbnail : PostAction()
}