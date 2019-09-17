package a.dev.mobile.gthread


import android.content.Context
import android.database.Cursor
import java.util.ArrayList
import android.graphics.PorterDuff
import androidx.core.content.ContextCompat
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import java.text.DecimalFormat

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
object ConstText{





    const val GMODEL_INTENT = "g_model"
    const val GMODEL_MM = 1
    const val GMODEL_IN = 2



}









object HelpMy {
    var contextGetter: (() -> Context)? = null

    fun mmToInch(mm: String): String {
        val decimalFormat = DecimalFormat("###.####")
        var value = mm.replace(",", ".")
        value = try {
            decimalFormat.format(java.lang.Double.parseDouble(value) / 25.4).replace(",", ".")
        } catch (e: NumberFormatException) {
            "-"
        }


        return value
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

    fun toPrimitive(arrayDouble: Array<Double>?): DoubleArray? {
        if (arrayDouble == null) {
            return null
        } else if (arrayDouble.size == 0) {
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
}

