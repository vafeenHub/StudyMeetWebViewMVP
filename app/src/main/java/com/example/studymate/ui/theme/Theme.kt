package com.example.studymate.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


internal data class AppThemeColors(
	val background: Color,
)

private val basePalette = AppThemeColors(
	background = Color.White,
)

private val baseDarkPalette = basePalette.copy(
	background = Color.Black,
)


@Composable
fun MainTheme(
	darkTheme: Boolean = isSystemInDarkTheme(),
	content: @Composable () -> Unit,
) {

	val colors = if (darkTheme) {
		baseDarkPalette
	} else {
		basePalette
	}

	CompositionLocalProvider(
		LocalColors provides colors,
		content = content
	)
}

internal object AppTheme {
	val colors: AppThemeColors
		@Composable
		get() = LocalColors.current
}

private val LocalColors = staticCompositionLocalOf<AppThemeColors> {
	error("Composition error")
}