package com.example.android.politicalpreparedness.election

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.navigation.NavDirections
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import com.example.android.politicalpreparedness.repository.ElectionsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VoterInfoViewModel(app: Application) : AndroidViewModel(app) {


    private val _navigationAddress = MutableLiveData<NavDirections?>()
    val navigationAddress: LiveData<NavDirections?>
        get() = _navigationAddress

    private val database = ElectionDatabase.getInstance(app.applicationContext)
    private val electionsRepository = ElectionsRepository(database)


    //TODO: Add live data to hold voter info
    private val _voterInfo = MutableLiveData<VoterInfoResponse>()
    val voterInfo: LiveData<VoterInfoResponse>
        get() = _voterInfo

    //TODO: Add var and methods to populate voter info

    fun fetchVoterInfo(electionId: Int, state: String) {
        viewModelScope.launch {
            Log.i("VoterInfoFragment -", "id = ${electionId}, state = ${state}")
            getVoterInfo(electionId, state)
        }
    }

    private suspend fun getVoterInfo(electionId: Int, state: String) {
        withContext(Dispatchers.IO) {
            try {
//                _voterInfo.value = CivicsApi.retrofitService.getVoterInfoResults(electionId, state)
                val response = CivicsApi.retrofitService.getVoterInfoResults(electionId, state)
                _voterInfo.postValue(response)
                Log.i("VoterInfoViewModel", "${response?.state?.get(0)?.electionAdministrationBody?.electionInfoUrl}  ---")
                Log.i("VoterInfoViewModel", response.election.name)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.i("Error-- ","Some error here")
            }
        }
    }

    //TODO: Add var and methods to support loading URLs

    //TODO: Add var and methods to save and remove elections to local database
    //TODO: cont'd -- Populate initial state of save button to reflect proper action based on election saved status

    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */

}