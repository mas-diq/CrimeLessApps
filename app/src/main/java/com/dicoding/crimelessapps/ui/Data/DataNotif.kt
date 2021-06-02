package com.dicoding.crimelessapps.ui.Data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataNotif(
    var icon: String = "null",
    var title: String = "null",
    var desc: String = "null",
    var time: String = "null",
) : Parcelable