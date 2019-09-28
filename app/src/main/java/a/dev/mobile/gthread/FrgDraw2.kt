package a.dev.mobile.gthread

import a.dev.mobile.gthread.HelpMy.arrayFloatFromCursor
import a.dev.mobile.gthread.HelpMy.arrayIntegerFromCursor
import a.dev.mobile.gthread.HelpMy.getMaxValue
import a.dev.mobile.gthread.HelpMy.getMinValue
import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import kotlin.math.abs
import kotlin.math.min

class FrgDraw2 : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.draw, container, false)
        val ll: LinearLayout = root.findViewById(R.id.custom_view)

        ll.addView(context?.let { DrawView2D(it, null, 0) })

        return root
    }

    class DrawView2D @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    ) : View(context, attrs, defStyleAttr) {


        private lateinit var canvas: Canvas

        private lateinit var paint: Paint

        private lateinit var path: Path
        private lateinit var db: SQLiteDatabase
        private lateinit var cursor: Cursor
        private var h: Float = 0.0f
        private var w: Float = 0.0f
        lateinit var xArray: FloatArray
        lateinit var yArray: FloatArray
        lateinit var draw: IntArray

        @SuppressLint("DrawAllocation")
        override fun onDraw(c: Canvas) {
            this.canvas = c
            db = DbSQLiteHelper(context).readableDatabase




            canvas.drawARGB(80, 102, 204, 255)
            paint = Paint()
            path = Path()



            h = height.toFloat()
            w = width.toFloat()
            val prop: Float = h / w

            Log.i(TAG, "height == $height")
            Log.i(TAG, "width == $width")
            Log.i(TAG, "prop == $prop")


            paint.strokeWidth = 2F
            paint.style = Paint.Style.STROKE
            paint.color = Color.BLACK

            //   drawFromArray("select * from d4", CENTER, true)
            drawFromArray("select * from d2", CENTER, true)
            //    drawFromArray("select * from d5", TOP_RIGHT, false)
            //    drawFromArray("select * from d5", CENTER, true)

            //drawFromArray("select * from d3", TOP_LEFT)
            // drawFromArray("select * from d1", TOP_LEFT)
            //  drawFromArray("select * from d1", TOP_LEFT)
        }

        private fun drawFromArray(query: String, position: Int, isStretch: Boolean) {

            cursor = db.rawQuery(query, null)
            xArray = arrayFloatFromCursor(cursor, "_X")
            yArray = arrayFloatFromCursor(cursor, "_Y")
            draw = arrayIntegerFromCursor(cursor, "_DRAW")

            val matrix = Matrix()
            matrix.reset()
            path.reset()

            val xMax = getMaxValue(xArray)
            val yMax = getMaxValue(yArray)

            val xMin = getMinValue(xArray)
            val yMin = getMinValue(yArray)

            val xCenter = xMin + ((xMax - xMin) / 2)
            val yCenter = yMin + ((yMax - yMin) / 2)

            val wCenter = w / 2
            val hCenter = h / 2

            //коефицент чтобы контур не залезал на экран
            val kof = 0.95f
            val scaleMin = min(w / xMax, h / yMax) * kof

            var xCord: Float
            var yCord: Float
            Log.i(TAG, "x max    = $xMax")
            Log.i(TAG, "x min    = $xMin")
            Log.i(TAG, "x center = $xCenter")

            Log.i(TAG, "y max    = $yMax")
            Log.i(TAG, "y min    = $yMin")
            Log.i(TAG, "y center = $yCenter")

            if (position == CENTER) {

                for ((i, value) in draw.withIndex()) {
                    xCord = xArray[i]
                    yCord = yArray[i]
                    if (value == 0) {
                        path.moveTo(xCord, (yCord))
                    } else {
                        path.lineTo(xCord, (yCord))
                    }
                }
                //сделать зеркально
                matrix.setScale(1f, -1f, xCenter, yCenter)
                matrix.postTranslate(w / 2 - xCenter, h / 2 - yCenter)
                if (isStretch) matrix.postScale(scaleMin, scaleMin, wCenter, hCenter)
            } else if (position == TOP_LEFT) {

                for ((i, value) in draw.withIndex()) {
                    xCord = xArray[i]
                    yCord = yArray[i]
                    if (value == 0) {
                        path.moveTo(xCord, yCord)
                    } else {
                        path.lineTo(xCord, yCord)
                    }
                    // matrix.setRotate(180f, xCenter, yCenter)
                    //  matrix.setScale(scaleMin, scaleMin,-xMin, -yMin)
                }
                matrix.setScale(1f, -1f, xCenter, yCenter)
                if (isStretch) matrix.postScale(scaleMin, scaleMin, xMin, yMin)
                matrix.postTranslate(abs(xMin + xMin * kof), abs(yMin + yMin * kof))
            } else if (position == TOP_RIGHT) {

                for ((i, value) in draw.withIndex()) {
                    xCord = xArray[i]
                    yCord = yArray[i]
                    if (value == 0) {
                        path.moveTo(xCord, yCord)
                    } else {
                        path.lineTo(xCord, yCord)
                    }
                    // matrix.setRotate(180f, xCenter, yCenter)
                    //  matrix.setScale(scaleMin, scaleMin,-xMin, -yMin)
                }
                matrix.setScale(1f, -1f, xCenter, yCenter)
                matrix.postTranslate(w - xMax - abs(xMin) * kof, abs(yMin + yMin * kof))
            }

            path.transform(matrix)
            canvas.drawPath(path, paint)
        }

        companion object {
            private const val TAG = "== FrgDraw"
            private const val CENTER = 1
            private const val TOP_LEFT = 2
            private const val TOP_RIGHT = 3
        }
    }
}