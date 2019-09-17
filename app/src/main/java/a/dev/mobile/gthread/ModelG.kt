
package a.dev.mobile.gthread

import java.io.Serializable

data class ModelG(

    val id: Int,

    var desc1: String,
    val desc2: String,

    val threadPitch: String,
    val threadPer: String,

    val class_: String,

    val exMajorDiaMax: String,
    val exMajorDiaMin: String,
    val exPitchDiaMax: String,

    val exPitchDiaMin: String,
    val exMinorDiaMax: String,

    val inMinorDiaMin: String,
    val inMinorDiaMax: String,

    val inPitchDiaMax: String,
    val inPitchDiaMin: String,

    val inMajorDiaMin: String,

    val inTapDrill: String,

    val imageResId: Int
) : Serializable
