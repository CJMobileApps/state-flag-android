package com.cjmobileapps.stateflagandroid.ui.list

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.AbstractListDetailFragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import com.cjmobileapps.stateflagandroid.R
import com.cjmobileapps.stateflagandroid.data.model.StateFlagData
import com.cjmobileapps.stateflagandroid.databinding.FragmentStateFlagListBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StateFlagListFragment : AbstractListDetailFragment() {

    lateinit var binding: FragmentStateFlagListBinding

    private val viewModel: StateFlagListViewModel by viewModels()

    override fun onCreateListPaneView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStateFlagListBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onListPaneViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onListPaneViewCreated(view, savedInstanceState)

        slidingPaneLayout.lockMode = SlidingPaneLayout.LOCK_MODE_LOCKED

        binding.stateFlagListStateFlagsList.layoutManager =
            LinearLayoutManager(requireContext())

        val adapter = StateFlagListAdapter(onStateFlagClicked = { stateFlagData ->
            openDetails(stateFlagData)
        })
        binding.stateFlagListStateFlagsList.adapter = adapter

        viewModel.stateLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is StateFlagListViewModel.StateFlagListState.StateFlagListStateLoaded -> {
                    val stateFlagDataList = state.stateFlagDataList

                    if(adapter.stateFlagDataList != stateFlagDataList) {
                        adapter.stateFlagDataList = stateFlagDataList
                        adapter.notifyItemRangeChanged(0, stateFlagDataList.size)
                    }
                    hideLoading()

                    if (stateFlagDataList.isNotEmpty()) {
                        binding.stateFlagListNoStateFlagsFound.visibility = View.GONE
                        binding.stateFlagListStateFlagsList.visibility = View.VISIBLE
                    } else {
                        binding.stateFlagListNoStateFlagsFound.visibility = View.VISIBLE
                        binding.stateFlagListStateFlagsList.visibility = View.GONE
                    }

                    state.showError.observe(viewLifecycleOwner) { showError ->
                        if (showError) {
                            Snackbar.make(
                                requireView(),
                                getString(R.string.error_occurred),
                                Snackbar.LENGTH_LONG
                            ).show()
                            state.errorWasShown()
                        }
                    }
                }

                is StateFlagListViewModel.StateFlagListState.StateFlagListLoading -> {
                    showLoading()
                }

                else -> throw java.lang.IllegalStateException("View State Doesn't Exist")
            }
        }

        binding.stateFlagListSwipeRefresh.setOnRefreshListener {
            viewModel.getStateFlagDataList()
            hideLoading()
        }
    }

    override fun onCreateDetailPaneNavHostFragment(): NavHostFragment {
        return NavHostFragment.create(R.navigation.detail_nav_graph)
    }

    private fun openDetails(stateFlagData: StateFlagData) {
        val bundle = bundleOf("stateFlagData" to stateFlagData)

        val detailNavController = detailPaneNavHostFragment.navController
        detailNavController.navigate(
            R.id.stateFlagDetailFragment,
            bundle,
            NavOptions.Builder()
                .setPopUpTo(detailNavController.graph.startDestinationId, true)
                .apply {
                    if (slidingPaneLayout.isOpen) {
                        setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
                        setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
                    }
                }
                .build()
        )
        slidingPaneLayout.open()
    }

    private fun showLoading() {
        binding.stateFlagListSwipeRefresh.isRefreshing = true
    }

    private fun hideLoading() {
        binding.stateFlagListSwipeRefresh.isRefreshing = false

    }
}
