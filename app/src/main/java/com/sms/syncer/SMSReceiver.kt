package com.sms.syncer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date

class SmsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "android.provider.Telephony.SMS_RECEIVED") {
            val bundle: Bundle? = intent.extras
            if (bundle != null) {
                val pdusObj = bundle["pdus"] as Array<*>

                for (i in pdusObj.indices) {
                    val currentMessage = createFromPdu(pdusObj[i] as ByteArray?)
                    val phoneNumber: String = currentMessage.displayOriginatingAddress
                    val message: String = currentMessage.displayMessageBody

                    Log.i("SmsReceiver", "senderNum: $phoneNumber; message: $message")
                    val sms = mapOf(
                        "phoneNo" to phoneNumber,
                        "message" to message,
                        "time" to getDate(),
                        "milliTime" to currentMessage.timestampMillis
                    )

                    FirebaseFirestore.getInstance().collection("Sample")
                        .add(sms)
                        .addOnSuccessListener {
                                ref->
                            Toast.makeText(context, "Msg saved", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                                e->
                            Toast.makeText(context,"Failure", Toast.LENGTH_SHORT).show()
                        }
                    // Show Alert
                    val duration = Toast.LENGTH_LONG
                    val toast = Toast.makeText(
                        context,
                        "Sender: $phoneNumber, Message: $message",
                        duration
                    )
                    toast.show()
                }
            }
        }
    }

    private fun createFromPdu(pdu: ByteArray?): SmsMessage {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            SmsMessage.createFromPdu(pdu, "3gpp")
        } else {
            @Suppress("DEPRECATION")
            SmsMessage.createFromPdu(pdu)
        }
    }

    fun getDate(): String{
       val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
        return formatter.format(Date()).toString()
    }
}
