package com.example.android.politicalpreparedness.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.android.politicalpreparedness.network.models.Election

@Dao
interface ElectionDao {

    //TODO: Add insert query
    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = Election::class)
    fun insertElections(elections: List<Election>)

    //TODO: Add select all election query
    @Query("SELECT * FROM election_table ORDER BY electionDay ASC")
    fun getAllElections(): LiveData<List<Election>>

    //TODO: Add select single election query
    @Query("SELECT * FROM election_table WHERE id = :id LIMIT 1")
    suspend fun getElection(id: Int): Election?

    //TODO: Add delete query

    //TODO: Add clear query
    @Query("DELETE FROM election_table")
    suspend fun removeAll()

}