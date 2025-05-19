package com.vinay.walmartassessment.presentation.view

import androidx.recyclerview.widget.RecyclerView
import com.example.walmartassessment.databinding.CountriesItemBinding
import com.vinay.walmartassessment.domain.data.Country

class CountyViewHolder(val binding: CountriesItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(country:Country){
        with(binding){
            tvCountryNameRegion.text = "${country.countryName}, ${country.countryRegion}"
            tvCode.text = country.countryCode
            tvCapital.text = country.countryCapital
        }
    }
}