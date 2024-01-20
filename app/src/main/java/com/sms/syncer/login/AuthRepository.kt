package com.sms.syncer.login

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()

    fun signInWithGoogle(account: GoogleSignInAccount, callback: (Boolean)->Unit){
        val credential = GoogleAuthProvider.getCredential(account.idToken,null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task->
                if(task.isSuccessful){
                    callback(true)
                }  else {
                    callback(false)
                }
            }
    }

    fun signout(){
        auth.signOut()
    }
}