package com.fatihbilgin.movieapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CampaignViewModel @Inject constructor() : ViewModel() {
    private val _selectedCampaignId = MutableLiveData<Int?>(null)
    val selectedCampaignId: LiveData<Int?> = _selectedCampaignId

    fun selectCampaign(campaignId: Int) {
        _selectedCampaignId.value = campaignId
    }

    fun clearSelection() {
        _selectedCampaignId.value = null
    }
}