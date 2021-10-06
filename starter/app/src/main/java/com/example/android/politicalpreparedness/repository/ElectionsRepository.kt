package com.example.android.politicalpreparedness.repository

import android.util.Log
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.FollowedElection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//class ElectionsRepository(private val database: ElectionDatabase) {
class ElectionsRepository(private val database: ElectionDao) {

    val currentElections = database.getAllElections()

    suspend fun refreshElections() {
        withContext(Dispatchers.IO) {
            try {
                val electionResults = CivicsApi.retrofitService.getElectionsResults()

                database.insertElections(electionResults.elections)
            } catch (exception: Exception) {
                exception.printStackTrace()
            }

        }
    }

    suspend fun isElectionSaved(electionId: Int): Boolean? {
        return withContext(Dispatchers.IO) {
            return@withContext database.findElectionId(electionId) != null
        }
    }

    suspend fun insertFollowedElection(electionId: Int) {
        withContext(Dispatchers.IO) {
            database.insertFollowed(FollowedElection(electionId))
        }

    }

}