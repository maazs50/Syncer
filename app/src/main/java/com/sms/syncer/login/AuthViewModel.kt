package com.sms.syncer.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class AuthViewModel(): ViewModel() {
    private val repo = AuthRepository()
    private val _loginResult = MutableLiveData<Boolean>()
    private val _user = MutableLiveData<User?>()
    val loginResult: LiveData<Boolean>
        get() = _loginResult
    val user: LiveData<User?>
        get() = _user

    fun loginWithGoogle(googleSignInAccount: GoogleSignInAccount){
        repo.signInWithGoogle(googleSignInAccount){result->
            _loginResult.value = result
            if (result){
                _user.value = User(googleSignInAccount.id,googleSignInAccount.email)
            }
        }
    }

    fun signUpWithGoogle(googleSignInAccount: GoogleSignInAccount){
        repo.signInWithGoogle(googleSignInAccount){result->
            _loginResult.value = result
            if (result){
                _user.value = User(googleSignInAccount.id,googleSignInAccount.email)
            }
        }
    }

    fun signOut(){
        repo.signout()
        _user.value = null
    }
}