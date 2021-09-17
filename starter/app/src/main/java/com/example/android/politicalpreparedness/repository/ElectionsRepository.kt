package com.example.android.politicalpreparedness.repository

import android.util.Log
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.CivicsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ElectionsRepository(private val database: ElectionDatabase) {

    val currentElections = database.electionDao.getAllElections()

    suspend fun refreshElections() {
        withContext(Dispatchers.IO) {
            try {
                val electionResults = CivicsApi.retrofitService.getElectionsResults()

                database.electionDao.insertElections(electionResults.elections)
            } catch (exception: Exception) {
                exception.printStackTrace()
            }

        }
    }
}