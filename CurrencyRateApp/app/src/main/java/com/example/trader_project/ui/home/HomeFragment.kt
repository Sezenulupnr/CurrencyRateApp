package com.example.trader_project.ui.home

import HomeAdapter
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.trader_project.R
import com.example.trader_project.common.gone
import com.example.trader_project.common.viewBinding
import com.example.trader_project.common.visible
import com.example.trader_project.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val viewModel by viewModels<HomeViewModel>()

    private val homeAdapter by lazy { HomeAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            rvCurrency.adapter = homeAdapter

            btnLogOut.setOnClickListener {
                viewModel.logOut()
            }
        }

        viewModel.getPortfolio()
        viewModel.getDate()
        observeData()
    }

    private fun observeData() = with(binding) {
        viewModel.homeState.observe(viewLifecycleOwner) { state ->
            when (state) {
                HomeState.Loading -> progressBar.visible()

                is HomeState.SuccessState -> {
                    progressBar.gone()
                    homeAdapter.submitList(state.currencyList)
                    Log.v("başarılı", state.toString())
                }

                is HomeState.EmptyScreen -> {
                    progressBar.gone()
                    ivError.visible()
                    tvError.visible()
                    tvError.text = state.failMessage
                    Snackbar.make(requireView(), state.failMessage, 1000).show()
                }

                is HomeState.ShowPopUp -> {
                    progressBar.gone()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }

                HomeState.GoToLogOut -> {
                    findNavController().navigate(R.id.signInFragment)
                }
            }

            viewModel.dateState.observe(viewLifecycleOwner) { state ->
                when (state) {
                    DateState.Loading -> progressBar.visible()

                    is DateState.SuccessState -> {
                        progressBar.gone()
                        tvDate.text =
                            "${state.date} \n Tarihinde Belirlenen \n Türkiye Cumhuriyet Merkez Bankası Kurları"
                    }

                    is DateState.EmptyScreen -> {
                        progressBar.gone()
                        ivError.visible()
                        tvError.visible()
                        tvError.text = state.failMessage
                        Snackbar.make(requireView(), state.failMessage, 1000).show()
                    }

                    is DateState.ShowPopUp -> {
                        progressBar.gone()
                        Snackbar.make(requireView(), state.errorMessage, 1000).show()
                    }
                }
            }
        }
    }
}