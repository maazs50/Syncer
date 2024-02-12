package com.sms.syncer.login

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.sms.syncer.R
import com.sms.syncer.toast


/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {

    private lateinit var viewModel: AuthViewModel
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var loginBtn: SignInButton
    private val READ_SMS_PERMISSION_REQUEST_CODE = 123

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_login, container, false)
        loginBtn = rootView.findViewById(R.id.loginBtn)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        checkSmsPermission()
// Configure Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(),gso)
        googleSignInClient.revokeAccess()
        loginBtn.setOnClickListener{
            signInWithGoogle()
        }
        viewModel.loginResult.observe(viewLifecycleOwner){
            success->
            if (success){
                findNavController().navigate(R.id.action_loginFragment_to_displayFrament)
                toast("Success")
            } else {
                toast("Login Failed")
            }
        }
    }

    fun signInWithGoogle(){
        val signInIntent =googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                viewModel.loginWithGoogle(account!!)
            } catch (e: ApiException) {
                // Handle sign-in failure
                Log.w(TAG, "Google sign-in failed", e)
                // You may want to display an error message to the user
            }
        }
    }
    fun checkSmsPermission(){

// Check if permission is granted
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.READ_SMS
            ) == PackageManager.PERMISSION_GRANTED) {
            // Permission already granted, perform your SMS reading logic here
            readSms()
        } else {
            // Permission not granted, request it
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_SMS), READ_SMS_PERMISSION_REQUEST_CODE)
        }

        // Handle the result of the permission request

    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            READ_SMS_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, perform your SMS reading logic here
                    readSms()
                } else {
                    // Permission denied, handle accordingly (e.g., show a message or take alternative action)
                    ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_SMS), READ_SMS_PERMISSION_REQUEST_CODE)
                }
            }
            // Handle other permission request codes if needed
        }
    }

    fun readSms(){
        Toast.makeText(this.context, "Permission Granted", Toast.LENGTH_SHORT).show()
    }

    companion object{
        private const val RC_SIGN_IN = 123
        private const val TAG = "LoginFragment"
    }
}