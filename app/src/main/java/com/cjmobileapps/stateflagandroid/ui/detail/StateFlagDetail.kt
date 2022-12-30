package com.cjmobileapps.stateflagandroid.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cjmobileapps.stateflagandroid.R
import com.cjmobileapps.stateflagandroid.data.model.StateFlagData
import com.cjmobileapps.stateflagandroid.databinding.FragmentStateFlagDetailBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StateFlagDetail : Fragment() {

    lateinit var binding: FragmentStateFlagDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStateFlagDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val stateFlagData = arguments?.getParcelable<StateFlagData>("stateFlagData") ?: return

        binding.stateFlagDetailStateName.text = stateFlagData.title
        binding.stateFlagDetailUrl.text = stateFlagData.url
        binding.stateFlagDetailDescription.text = stateFlagData.description

        Picasso.get()
            .load(stateFlagData.thumbnail)
            .placeholder(R.color.stateFlag_black)
            .error(R.color.stateFlag_black)
            .into(binding.stateFlagDetailImage)

    }
}
