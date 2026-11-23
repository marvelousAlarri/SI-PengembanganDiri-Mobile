package com.example.sipengembangandiridtedi.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.compose.ui.unit.dp

private fun lucide(name: String, vararg paths: String): ImageVector =
    ImageVector.Builder(
        name = name,
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        paths.forEach { d ->
            addPath(
                pathData = addPathNodes(d),
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            )
        }
    }.build()

/** Ikon outline 24x24 bergaya Lucide. Warna diatur lewat parameter `tint` pada Icon(). */
object DtIcons {
    val Home: ImageVector by lazy {
        lucide(
            "home",
            "M15 21v-8a1 1 0 0 0-1-1h-4a1 1 0 0 0-1 1v8",
            "M3 10a2 2 0 0 1 .709-1.528l7-5.999a2 2 0 0 1 2.582 0l7 5.999A2 2 0 0 1 21 10v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"
        )
    }
    val File: ImageVector by lazy {
        lucide(
            "file",
            "M14.5 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V7.5L14.5 2z",
            "M14 2v6h6", "M16 13H8", "M16 17H8", "M10 9H8"
        )
    }
    val History: ImageVector by lazy {
        lucide("history", "M3 12a9 9 0 1 0 9-9 9.75 9.75 0 0 0-6.74 2.74L3 8", "M3 3v5h5", "M12 7v5l4 2")
    }
    val Book: ImageVector by lazy {
        lucide(
            "book",
            "M12 7v14",
            "M3 18a1 1 0 0 1-1-1V4a1 1 0 0 1 1-1h5a4 4 0 0 1 4 4 4 4 0 0 1 4-4h5a1 1 0 0 1 1 1v13a1 1 0 0 1-1 1h-6a3 3 0 0 0-3 3 3 3 0 0 0-3-3z"
        )
    }
    val Cap: ImageVector by lazy {
        lucide(
            "cap",
            "M21.42 10.922a1 1 0 0 0-.019-1.838L12.83 5.18a2 2 0 0 0-1.66 0L2.6 9.08a1 1 0 0 0 0 1.832l8.57 3.908a2 2 0 0 0 1.66 0z",
            "M22 10v6",
            "M6 12.5V16a6 3 0 0 0 12 0v-3.5"
        )
    }
    val Bell: ImageVector by lazy {
        lucide(
            "bell",
            "M10.268 21a2 2 0 0 0 3.464 0",
            "M3.262 15.326A1 1 0 0 0 4 17h16a1 1 0 0 0 .74-1.673C19.41 13.956 18 12.499 18 8A6 6 0 0 0 6 8c0 4.499-1.411 5.956-2.738 7.326"
        )
    }
    val Search: ImageVector by lazy {
        lucide("search", "M19 11a8 8 0 1 1-16 0 8 8 0 0 1 16 0z", "M21 21l-4.3-4.3")
    }
    val ChevronDown: ImageVector by lazy { lucide("chevron-down", "M6 9l6 6 6-6") }
    val ChevronUp: ImageVector by lazy { lucide("chevron-up", "M18 15l-6-6-6 6") }
    val ChevronRight: ImageVector by lazy { lucide("chevron-right", "M9 18l6-6-6-6") }
    val ArrowRight: ImageVector by lazy { lucide("arrow-right", "M5 12h14", "M12 5l7 7-7 7") }
    val ArrowLeft: ImageVector by lazy { lucide("arrow-left", "M19 12H5", "M12 19l-7-7 7-7") }
    val Plus: ImageVector by lazy { lucide("plus", "M5 12h14", "M12 5v14") }
    val Close: ImageVector by lazy { lucide("close", "M18 6L6 18", "M6 6l12 12") }
    val Trash: ImageVector by lazy {
        lucide(
            "trash",
            "M3 6h18",
            "M19 6v14c0 1-1 2-2 2H7c-1 0-2-1-2-2V6",
            "M8 6V4c0-1 1-2 2-2h4c1 0 2 1 2 2v2",
            "M10 11v6", "M14 11v6"
        )
    }
    val Wallet: ImageVector by lazy {
        lucide(
            "wallet",
            "M19 7V4a1 1 0 0 0-1-1H5a2 2 0 0 0 0 4h15a1 1 0 0 1 1 1v4h-3a2 2 0 0 0 0 4h3a1 1 0 0 0 1-1v-2a1 1 0 0 0-1-1",
            "M3 5v14a2 2 0 0 0 2 2h15a1 1 0 0 0 1-1v-4"
        )
    }
    val Coins: ImageVector by lazy {
        lucide(
            "coins",
            "M14 8a6 6 0 1 1-12 0 6 6 0 0 1 12 0z",
            "M18.09 10.37A6 6 0 1 1 10.34 18",
            "M7 6h1v4",
            "M16.71 13.88l.7.71-2.82 2.82"
        )
    }
    val LogOut: ImageVector by lazy {
        lucide("log-out", "M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4", "M16 17l5-5-5-5", "M21 12H9")
    }
    val CheckCircle: ImageVector by lazy {
        lucide("check-circle", "M22 12A10 10 0 1 1 2 12a10 10 0 0 1 20 0z", "M9 12l2 2 4-4")
    }
    val Folder: ImageVector by lazy {
        lucide(
            "folder",
            "M20 20a2 2 0 0 0 2-2V8a2 2 0 0 0-2-2h-7.9a2 2 0 0 1-1.69-.9L9.6 3.9A2 2 0 0 0 7.93 3H4a2 2 0 0 0-2 2v13a2 2 0 0 0 2 2z"
        )
    }
}