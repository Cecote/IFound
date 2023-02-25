package br.com.alura.orgs.database.converter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class Converterd {

    @TypeConverter
    fun deByteArrayParaBitmap(byteArrayBitmap: ByteArray?) : Bitmap? {
        return byteArrayBitmap?.let { BitmapFactory.decodeByteArray(byteArrayBitmap, 0, byteArrayBitmap.size) }
    }

    @TypeConverter
    fun deBitmapParaByteArray(byteArrayBitmap: Bitmap?) : ByteArray?{
        val outputStream = ByteArrayOutputStream()
        byteArrayBitmap?.let {
            byteArrayBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        }
        return outputStream.toByteArray()
    }
}