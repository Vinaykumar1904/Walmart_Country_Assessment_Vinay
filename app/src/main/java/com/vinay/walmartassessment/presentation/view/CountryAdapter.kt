package com.vinay.walmartassessment.presentation.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.walmartassessment.databinding.CountriesItemBinding
import com.vinay.walmartassessment.domain.data.Country

/*
* This class is the Recycler Adapter to display CountryDetails into Recycler view
* */
class CountryAdapter(): RecyclerView.Adapter<CountyViewHolder>() {
    private var countries:List<Country> = emptyList()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountyViewHolder {
        val binding = CountriesItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CountyViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CountyViewHolder,
        position: Int
    ) {
        holder.bind(countries[position])
    }

    override fun getItemCount(): Int = countries.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateCountries(newCountryList: List<Country>) {
        countries = newCountryList
        notifyDataSetChanged()
    }
}