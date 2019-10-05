package a.dev.mobile.gthread

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.VectorDrawable
import android.os.Build.VERSION_CODES
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import java.text.DecimalFormat
import java.util.ArrayList

object DBColumn {


    const val ID = "_id"
    const val DESC1 = "designation_1"
    const val DESC2 = "designation_2"
    const val THREAD_PITCH = "thread_pitch"
    const val THREAD_PER = "thread_per"
    const val CLASS_ = "class"
    const val EX_MAJOR_DIA_MAX = "ex_major_dia_max"
    const val EX_MAJOR_DIA_MIN = "ex_major_dia_min"
    const val EX_PITCH_DIAM_MAX = "ex_pitch_diam_max"
    const val EX_PITCH_DIAM_MIN = "ex_pitch_diam_min"
    const val EX_MINOR_DIA_MAX = "ex_minor_dia_max"
    const val IN_MINOR_DIA_MIN = "in_minor_dia_min"
    const val IN_MINOR_DIA_MAX = "in_minor_dia_max"
    const val IN_PITCH_DIAM_MIN = "in_pitch_diam_min"
    const val IN_PITCH_DIAM_MAX = "in_pitch_diam_max"
    const val IN_MAJOR_DIA_MIN = "in_major_dia_min"
    const val IN_TAP_DRILL = "in_tap_drill"
}

object ConstText {


    const val GMODEL_INTENT = "g_model"
}

object HelpMy {
    var contextGetter: (() -> Context)? = null

    fun mmToInch(mm: String): String {
        val decimalFormat = DecimalFormat("###.###")
        var value = mm.replace(",", ".")
        value = try {
            decimalFormat.format(java.lang.Double.parseDouble(value) / 25.4).replace(",", ".")
        } catch (e: NumberFormatException) {
            ""
        }


        return value
    }

    private fun formatDecimal(value: String, numberFromPoint: Int): String {

        var pattern = ""


        when (numberFromPoint) {
            1 -> pattern = "###.#"
            2 -> pattern = "###.##"
            3 -> pattern = "###.###"
            4 -> pattern = "###.####"
        }

        val decimalFormat = DecimalFormat(pattern)
        var text = value.replace(",", ".")
        text = decimalFormat.format(stringToDouble(text)).replace(",", ".")

        return text
    }

    fun formatDecimal(value: String): String {

        return formatDecimal(value, 3)
    }

    fun stringToDouble(value: String): Double {

        val text = value.replace(",", ".")
        return try {
            text.toDouble()
        } catch (e: NumberFormatException) {
            0.0
        }
    }

    fun doubleToString(value: Double): String {

        val text = value.toString().replace(",", ".")

        text.toDouble()
        return text
    }

    fun inchTomm(inch: String): String {
        val decimalFormat = DecimalFormat("###.####")
        var value = inch.replace(",", ".")
        value = try {
            decimalFormat.format(java.lang.Double.parseDouble(value) * 25.4).replace(",", ".")
        } catch (e: NumberFormatException) {
            "-"
        }

        return value
    }

    fun arrayStringFromCursor(cursor: Cursor, nameColumn: String): Array<String> {
        var i = -1

        val list = ArrayList<String>()


        if (cursor.moveToFirst()) {
            do {
                i++
                if (cursor.getString(cursor.getColumnIndex(nameColumn)) != null) {
                    list.add(cursor.getString(cursor.getColumnIndex(nameColumn)))
                } else {
                    list.add("")
                }
            } while (cursor.moveToNext())
        }

        return list.toTypedArray()
    }

    fun arrayIntegerFromCursor(cursor: Cursor, nameColumn: String): IntArray {
        var i = -1
        val values = IntArray(cursor.count)
        if (cursor.moveToFirst()) {
            do {
                i++
                values[i] = cursor.getInt(cursor.getColumnIndex(nameColumn))
            } while (cursor.moveToNext())
        }

        return values
    }

    fun arrayDoubleFromCursor(cursor: Cursor, nameColumn: String): DoubleArray? {
        val i = -1
        val arrayList = ArrayList<Double>()
        if (cursor.moveToFirst()) {
            do {

                if (cursor.getDouble(cursor.getColumnIndex(nameColumn)) != 0.0) {
                    arrayList.add(cursor.getDouble(cursor.getColumnIndex(nameColumn)))
                }
            } while (cursor.moveToNext())
        }

        return toPrimitive(arrayList.toTypedArray())
    }

    private fun toPrimitive(arrayDouble: Array<Double>?): DoubleArray? {
        if (arrayDouble == null) {
            return null
        } else if (arrayDouble.isEmpty()) {
            throw NullPointerException("arrayDouble.length == 0")
        }
        val result = DoubleArray(arrayDouble.size)
        for (i in arrayDouble.indices) {
            result[i] = arrayDouble[i]
        }
        return result
    }

    fun toPrimitive(doubleArrayList: ArrayList<Double>?): DoubleArray? {
        if (doubleArrayList == null) {
            return null
        } else if (doubleArrayList.size == 0) {
            throw NullPointerException("doubleArrayList.size == 0")
        }
        return toPrimitive(doubleArrayList.toTypedArray())
    }


    fun getMaxValue(array: FloatArray): Float {
        var maxValue = array[0]
        for (i in 1 until array.size) {
            if (array[i] > maxValue) {
                maxValue = array[i]
            }
        }
        return maxValue
    }

    fun getMinValue(array: FloatArray): Float {
        var minValue = array[0]
        for (i in 1 until array.size) {
            if (array[i] < minValue) {
                minValue = array[i]
            }
        }
        return minValue
    }



    fun arrayFloatFromCursor(cursor: Cursor, nameColumn: String): FloatArray {
        var i = -1
        val values = FloatArray(cursor.count)
        if (cursor.moveToFirst()) {
            do {
                i++
                values[i] = cursor.getFloat(cursor.getColumnIndex(nameColumn))
            } while (cursor.moveToNext())
        }

        return values
    }


    private fun getBitmap(vectorDrawable: VectorDrawable): Bitmap {
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawable.draw(canvas)
        return bitmap
    }

    private fun getBitmap(vectorDrawable: VectorDrawableCompat): Bitmap {
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawable.draw(canvas)
        return bitmap
    }


    @RequiresApi(VERSION_CODES.LOLLIPOP)
    fun getBitmap(context: Context, @DrawableRes drawableResId: Int): Bitmap {
        val drawable = ContextCompat.getDrawable(context, drawableResId)
        return when (drawable) {
            is BitmapDrawable -> drawable.bitmap
            is VectorDrawableCompat -> getBitmap((drawable as VectorDrawableCompat?)!!)
            is VectorDrawable -> getBitmap((drawable as VectorDrawable?)!!)
            else -> throw IllegalArgumentException("Unsupported drawable type")
        }
    }

}

