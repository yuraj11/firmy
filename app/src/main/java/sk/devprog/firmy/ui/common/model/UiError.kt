package sk.devprog.firmy.ui.common.model

import androidx.annotation.DrawableRes
import sk.devprog.firmy.util.TextResource

data class UiError(
    val text: TextResource,
    @get:DrawableRes val icon: Int
)
