package com.unt.shpe.design

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * SHPE app color scheme.
 * Maps 1:1 with iOS Brand colors.
 */
private val ShpeColors = lightColorScheme(
    primary = Brand.green,
    background = Brand.background,
    surface = Brand.background,
    onSurface = Brand.ink,
    onBackground = Brand.ink,
)

@Composable
fun ShpeTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ShpeColors,
        content = content,
    )
}
