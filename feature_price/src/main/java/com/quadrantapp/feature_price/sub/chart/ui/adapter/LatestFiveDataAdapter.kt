package com.quadrantapp.feature_price.sub.chart.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.quadrantapp.feature_price.R
import com.quadrantapp.feature_price.databinding.LatestFiveDataItemBinding
import com.quadrantapp.service_price.domain.entity.PriceHistoryEntity

class LatestFiveDataAdapter:
    ListAdapter<PriceHistoryEntity, LatestFiveDataAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.latest_five_data_item, parent, false)
        val binding = LatestFiveDataItemBinding.bind(rootView)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: LatestFiveDataItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: PriceHistoryEntity) {
            binding.apply {
                tvTime.text = "Time: ${data.updatedISO}"
                tvUSD.text = "USD: ${data.USD}"
                tvEUR.text = "EUR: ${data.EUR}"
                tvGBP.text = "GBP: ${data.GBP}"
                tvLatitude.text = "Latitude: ${data.latitude}"
                tvLongitude.text = "Longitude: ${data.longitude}"
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<PriceHistoryEntity>() {
            override fun areItemsTheSame(
                oldItem: PriceHistoryEntity,
                newItem: PriceHistoryEntity
            ): Boolean {
                return oldItem.updatedTimestamp == newItem.updatedTimestamp
            }

            override fun areContentsTheSame(
                oldItem: PriceHistoryEntity,
                newItem: PriceHistoryEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}