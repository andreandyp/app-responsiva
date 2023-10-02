package com.andreandyp.responsiveapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.andreandyp.responsiveapp.databinding.BeerListContentBinding
import com.andreandyp.responsiveapp.repository.models.Beer

private const val BEER_ITEM: Int = 0
private const val LOADING_ITEM: Int = 1

class BeerListAdapter(
    private val navController: NavController,
    private val parentActivity: AppCompatActivity,
    private val twoPane: Boolean,
    var beers: List<Beer> = emptyList()
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val showBeerDetails: View.OnClickListener

    init {
        showBeerDetails = View.OnClickListener { v ->
            val position = v.tag as Int
            val beer = beers[position]
            if (twoPane) {
                val fragment = BeerDetailFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable("beer", beer)
                    }
                }
                val textPlaceholder =
                    parentActivity.findViewById<TextView>(R.id.tv_details_beer_placeholder)
                textPlaceholder.visibility = View.GONE

                parentActivity.supportFragmentManager
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.beer_detail_container, fragment)
                    .commit()
            } else {
                val directions = BeerListFragmentDirections
                navController.navigate(directions.getBeerDetails(beer))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == LOADING_ITEM) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
            return LoadingViewHolder(view)
        }

        val binding: BeerListContentBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.beer_list_content,
            parent,
            false
        )

        return BeerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val beer = beers[position]
        if (holder is BeerViewHolder) {
            holder.binding.beer = beer
            with(holder.itemView) {
                tag = position
                setOnClickListener(showBeerDetails)
            }
        }
    }

    override fun getItemCount() = beers.size

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) LOADING_ITEM else BEER_ITEM
    }

    inner class BeerViewHolder(val binding: BeerListContentBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class LoadingViewHolder(view: View) :
        RecyclerView.ViewHolder(view)
}