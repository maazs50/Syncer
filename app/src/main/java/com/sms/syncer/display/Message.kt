package com.sms.syncer.display

import android.os.Parcelable
import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.parcelize.Parcelize

@Parcelize
data class Message(
    val message: String? = "",
    val milliTime: Long = 0,
    val phoneNo: String? = "",
    val time: String? = ""
) : Parcelable {
    constructor() : this("", 0,"", "")
    companion object{
        fun DocumentSnapshot.toUser(): Message?{
            try {
                val message = getString("message")!!
                val milliTime = getLong("milliTime")!!
                val phoneNo = getString("phoneNo")!!
                val time = getString("time")!!
                return Message(message,milliTime,phoneNo,time)
            } catch (e: Exception){
                Log.e(TAG, "Error converting user profile", e)
                FirebaseCrashlytics.getInstance().log("Error converting user profile")
                FirebaseCrashlytics.getInstance().setCustomKey("userId", id)
                FirebaseCrashlytics.getInstance().recordException(e)
                return null
            }
        }
        private const val TAG = "MessageClass"
    }
}