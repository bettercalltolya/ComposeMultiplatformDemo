package demo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = White100,
    onPrimary = Color.White,
    secondary = Green100,
    onSecondary = Color.White,
    background = Black,
    onBackground = Color.White,
    surface = Gray,
    onSurface = Color.White,
    onSurfaceVariant = White100,
    outline = LightGray
)

private val LightColorScheme = lightColorScheme(
    primary = Green300,
    onPrimary = Black,
    secondary = Green500,
    onSecondary = Color.White,
    background = White100,
    onBackground = Green500,
    surface = Green100,
    onSurface = Green500,
    onSurfaceVariant = Green300,
    outline = LightGray
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = Shapes,
        content = content
    )
}