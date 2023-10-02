package com.andreandyp.responsiveapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.andreandyp.responsiveapp.databinding.BeerDetailBinding

class BeerDetailFragment : Fragment() {
    private val args: BeerDetailFragmentArgs by navArgs()
    private lateinit var binding: BeerDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.beer_detail, container, false)
        binding.beer = args.beer

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = args.beer.name
    }

    override fun onDestroyView() {
        super.onDestroyView()

        val activity = requireActivity() as AppCompatActivity
        if (activity.supportFragmentManager.backStackEntryCount == 0) {
            val textPlaceholder =
                activity.findViewById<TextView>(R.id.tv_details_beer_placeholder)
            textPlaceholder?.visibility = View.VISIBLE
            activity.supportActionBar?.title = getString(R.string.title_beer_list)
        }
    }
}