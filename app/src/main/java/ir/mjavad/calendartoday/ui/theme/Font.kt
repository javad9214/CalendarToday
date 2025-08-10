package ir.mjavad.calendartoday.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

/**
 * Font utility class to easily access font families throughout the app
 */
object AppFont {
    // Main font families
    // val TitrCollage: FontFamily = ir.mjavad.calendartoday.ui.theme.TitrCollage


}

/**
 * Extension function to get bold version of a font
 */
@Composable
fun FontFamily.bold() = this to FontWeight.Bold

/**
 * Extension function to get normal weight version of a font
 */
@Composable
fun FontFamily.normal() = this to FontWeight.Normal