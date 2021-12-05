package com.zenjob.android.browsr.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.zenjob.android.browsr.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val args : DetailFragmentArgs by navArgs<DetailFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDetailBinding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.apply {
            Glide.with(binding.root.context)
                .load("https://www.themoviedb.org/t/p/w440_and_h660_face" + args?.detailFragmentArgs.image)
                .into(binding.detailImageView)

            binding.detailNameTextView.text = args.detailFragmentArgs.title
        }?.root

    }

}