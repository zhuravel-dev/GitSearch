package com.example.gitsearch.ui.firstFragment

import android.graphics.*
import com.squareup.picasso.Transformation

class CircleTransform : Transformation {

    override fun transform(source: Bitmap): Bitmap {
        val size = source.width.coerceAtMost(source.height)
        val x = (source.width - size) / 2
        val y = (source.height - size) / 2
        val squaredBitmap = Bitmap.createBitmap(source, x, y, size, size)
        if (squaredBitmap != source) {
            source.recycle()
        }

        val bitmap = Bitmap.createBitmap(size, size, source.config)
        val canvas = Canvas(bitmap)
        val shader =
            BitmapShader(squaredBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG or Paint.FILTER_BITMAP_FLAG)
        paint.shader = shader
        val r = size / 2f
        canvas.drawCircle(r, r, r - 1, paint)

        // create border
        val paintBorder = Paint()
        paintBorder.style = Paint.Style.STROKE
        paintBorder.color = Color.parseColor("#ffffff")
        paintBorder.isAntiAlias = true
        paintBorder.strokeWidth = 4f
        canvas.drawCircle(r, r, r - 1, paintBorder)

        squaredBitmap.recycle()
        return bitmap
    }

    override fun key(): String {
        return "circle"
    }
}