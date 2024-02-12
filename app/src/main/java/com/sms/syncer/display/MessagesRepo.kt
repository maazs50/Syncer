package com.sms.syncer.display

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class MessagesRepo() {
    val firestore = FirebaseFirestore.getInstance()

    fun getAllMessage(): LiveData<List<Message?>>{
        val liveData = MutableLiveData<List<Message?>>()
        firestore.collection("Sample")
            .orderBy("milliTime",Query.Direction.DESCENDING)
            .limit(15)
            .get()
            .addOnSuccessListener {result->
                val msgs = result.documents.map {
                    documentSnapshot ->
                    documentSnapshot.toObject(Message::class.java)

                }
                liveData.postValue(msgs)
                msgs.forEach {
                    Log.i("Message 101 ",it.toString())
                }
            }
            .addOnFailureListener {error->
                Log.e("Retrieve error", error.message!!)
            }
        return liveData
    }
}