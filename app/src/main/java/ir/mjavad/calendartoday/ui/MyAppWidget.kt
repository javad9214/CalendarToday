package ir.mjavad.calendartoday.ui

// Advanced blur widget with better frosted glass effect

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.unit.ColorProvider
import ir.mjavad.calendartoday.util.FarsiDateUtil.getDayOfWeek
import ir.mjavad.calendartoday.util.FarsiDateUtil.getFormattedDate
import ir.mjavad.calendartoday.util.FarsiDateUtil.getTodayFormatted
import ir.mjavad.calendartoday.util.FarsiDateUtil.getTodayPersianDateTriple

class BlurredTextWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = BlurredTextWidget()
}

class BlurredTextWidget : GlanceAppWidget() {



    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            Log.i(TAG, "provideGlance: today date is : ${getTodayFormatted()} ")
            val bitmap = renderTextToBitmap(context, getTodayFormatted())
            BlurredTextWidgetContent( bitmap)
        }
    }

    companion object {
        private const val TAG = "BlurredTextWidget"
    }
}

@Composable
private fun BlurredTextWidgetContent(
    bitmap: Bitmap
) {

    Box(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(ColorProvider(Color(0x30FFFFFF))) // Very transparent white
            .cornerRadius(20.dp)
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        // First blur layer
        Box(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(ColorProvider(Color(0x40FFFFFF))) // Semi-transparent white
                .cornerRadius(16.dp)
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            // Second blur layer
            Box(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .background(ColorProvider(Color(0x60000000))) // Semi-transparent dark
                    .cornerRadius(12.dp)
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    provider = ImageProvider(bitmap),
                    contentDescription = null
                )
            }
        }
    }
}