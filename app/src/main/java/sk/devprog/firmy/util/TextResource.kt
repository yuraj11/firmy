package sk.devprog.firmy.util

import android.content.Context
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * Abstracts Android string resource types into single type.
 */
sealed interface TextResource {
    data class Raw(val text: String) : TextResource

    data class Id(
        @StringRes val textResId: Int,
        val args: List<Any> = emptyList()
    ) : TextResource {
        constructor(@StringRes textResId: Int, vararg args: Any) : this(textResId, args.toList())
    }

    data class Plural(
        @PluralsRes val textResId: Int,
        val count: Int,
        val args: List<Any> = emptyList()
    ) : TextResource {
        constructor(@PluralsRes textResId: Int, count: Int, vararg args: Any) : this(textResId, count, args.toList())
    }

    fun asString(context: Context): String =
        when (this) {
            is Raw -> {
                text
            }

            is Id -> {
                context.resources.getString(textResId, *args.resolveArguments(context))
            }

            is Plural -> {
                context.resources.getQuantityString(textResId, count, *args.resolveArguments(context))
            }
        }

    @Composable
    fun asString(): String = asString(LocalContext.current)

    private fun List<Any>.resolveArguments(context: Context) =
        map { arg ->
            if (arg is TextResource) {
                arg.asString(context)
            } else {
                arg
            }
        }.toTypedArray()
}

fun String.toTextResource(): TextResource = TextResource.Raw(this)
