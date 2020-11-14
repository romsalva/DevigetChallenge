package com.deviget.reddiget.presentation.view

sealed class PostAction {
    object Click : PostAction()
    object Dismiss : PostAction()
    object ClickThumbnail : PostAction()
}