package com.example.android.politicalpreparedness.election

import android.app.Application
import androidx.lifecycle.*
import androidx.navigation.NavDirections
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.repository.ElectionsRepository
import kotlinx.coroutines.launch

//TODO: Construct ViewModel and provide election datasource
class ElectionsViewModel(app: Application) : AndroidViewModel(app) {


    private val _navigationAddress = MutableLiveData<NavDirections?>()
    val navigationAddress: LiveData<NavDirections?>
        get() = _navigationAddress

    private val database = ElectionDatabase.getInstance(app.applicationContext)
    private val electionsRepository = ElectionsRepository(database)

    //TODO: Create live data val for upcoming elections
    val upcomingElections = electionsRepository.currentElections

    //TODO: Create live data val for saved elections

    //TODO: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database

    init {
        refreshElections()
    }

    private fun refreshElections() {
        viewModelScope.launch {
            electionsRepository.refreshElections()
        }
    }

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