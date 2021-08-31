package com.example.android.politicalpreparedness.election

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.example.android.politicalpreparedness.network.models.Election

//TODO: Construct ViewModel and provide election datasource
class ElectionsViewModel(app: Application) : ViewModel() {


    private val _navigationAddress = MutableLiveData<NavDirections?>()
    val navigationAddress: LiveData<NavDirections?>
        get() = _navigationAddress

    //TODO: Create live data val for upcoming elections

    //TODO: Create live data val for saved elections

    //TODO: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database

    //TODO: Create functions to navigate to saved or upcoming election voter info
    fun upcomingElectionSelected(election: Election) {
        _navigationAddress.value = ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(election.id, election.division)
    }

    fun savedElectionSelected(election: Election) {
        _navigationAddress.value = ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(election.id, election.division)
    }

    fun doneNavigating() {
        _navigationAddress.value = null
    }

}