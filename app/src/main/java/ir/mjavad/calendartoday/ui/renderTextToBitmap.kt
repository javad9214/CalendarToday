package ir.mjavad.calendartoday.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.core.content.res.ResourcesCompat
import androidx.glance.Image
import androidx.glance.ImageProvider
import ir.mjavad.calendartoday.R
import java.io.File
import java.io.FileOutputStream
import androidx.core.graphics.createBitmap

fun renderTextToBitmap(context: Context, text: String): Bitmap {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textSize = 48f
        typeface = ResourcesCompat.getFont(context, R.font.roya_bold)
    }

    val width = paint.measureText(text).toInt()
    val height = (paint.fontMetrics.descent - paint.fontMetrics.ascent).toInt()

    return createBitmap(width, height).apply {
        val canvas = Canvas(this)
        canvas.drawText(text, 0f, -paint.fontMetrics.ascent, paint)
    }
}

private fun saveBitmapToCache(context: Context, bitmap: Bitmap, fileName: String) {
    val file = File(context.cacheDir, fileName)
    FileOutputStream(file).use {
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
    }
}
