package com.example.trader_project.ui.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.trader_project.R
import com.example.trader_project.databinding.FragmentSplashBinding
import com.example.trader_project.common.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val viewModel by viewModels<SplashViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun observeData() {
        viewModel.splashState.observe(viewLifecycleOwner) { state ->
            when (state) {
                SplashState.GoToSignIn -> {
                    findNavController().navigate(R.id.splashToSignIn)
                }

                SplashState.GoToSignUp -> {
                    findNavController().navigate(R.id.splashToSignUp)
                }

                SplashState.GoToHome -> {
                    findNavController().navigate(R.id.splashToHome)
                }
            }
        }
    }

}