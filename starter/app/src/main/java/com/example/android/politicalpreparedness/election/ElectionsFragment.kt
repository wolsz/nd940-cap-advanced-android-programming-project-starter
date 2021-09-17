package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.databinding.FragmentLaunchBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.election.adapter.ElectionListener

class ElectionsFragment: Fragment() {

    //TODO: Declare ViewModel
    private val viewModel : ElectionsViewModel by lazy {
        val application = requireNotNull(this.activity).application
        val viewModelFactory = ElectionsViewModelFactory(application)
        ViewModelProvider(this, viewModelFactory).get(ElectionsViewModel::class.java)
    }

    private lateinit var upcomingElectionListAdapter: ElectionListAdapter
    private lateinit var savedElectionListAdapter: ElectionListAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentElectionBinding.inflate(inflater)
//        val binding: FragmentElectionBinding = DataBindingUtil.inflate(
//                inflater,
//                R.layout.fragment_election,
//                container,
//                false)
        binding.lifecycleOwner = this

        //TODO: Add ViewModel values and create ViewModel

        //TODO: Add binding values
        binding.viewModel = viewModel

        //TODO: Link elections to voter info

        //TODO: Initiate recycler adapters
        upcomingElectionListAdapter = ElectionListAdapter(ElectionListener {
            viewModel.upcomingElectionSelected(it)
        })
        binding.upcomingElectionsList.adapter = upcomingElectionListAdapter

        savedElectionListAdapter = ElectionListAdapter(ElectionListener {
            viewModel.savedElectionSelected(it)
        })
        binding.savedElectionsList.adapter = savedElectionListAdapter

        viewModel.navigationAddress.observe(viewLifecycleOwner, Observer {
            directions ->
            directions?.let {
                this.findNavController().navigate(directions)
                viewModel.doneNavigating()
            }
        })
        //TODO: Populate recycler adapters
        return binding.root

    }

    //TODO: Refresh adapters when fragment loads

}