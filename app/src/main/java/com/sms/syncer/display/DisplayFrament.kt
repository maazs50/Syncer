package com.sms.syncer.display

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sms.syncer.R
import com.sms.syncer.login.AuthViewModel

class DisplayFrament : Fragment() {
    private lateinit var authViewModel: AuthViewModel
    private lateinit var logoutBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView =  inflater.inflate(R.layout.fragment_display_frament, container, false)
        logoutBtn = rootView.findViewById(R.id.logoutBtn)
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logoutBtn.setOnClickListener {
            authViewModel.signOut()
        }
        authViewModel.user.observe(viewLifecycleOwner){
            user->
            if (user == null){
                findNavController().navigate(R.id.action_displayFrament_to_loginFragment)
            }
        }
    }
}