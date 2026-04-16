package com.example.taskify

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfDocument
import java.io.File
import java.io.FileOutputStream

object PdfUtils {

    var lastFile: File? = null

    fun createPdf(context: Context, images: List<Bitmap>): File {

        val document = PdfDocument()

        images.forEachIndexed { index, bitmap ->
            val pageInfo = PdfDocument.PageInfo.Builder(
                bitmap.width,
                bitmap.height,
                index + 1
            ).create()

            val page = document.startPage(pageInfo)
            val canvas = page.canvas
            canvas.drawBitmap(bitmap, 0f, 0f, null)
            document.finishPage(page)
        }

        val file = File(context.getExternalFilesDir(null), "myfile.pdf")
        document.writeTo(FileOutputStream(file))
        document.close()

        lastFile = file
        return file
    }
}